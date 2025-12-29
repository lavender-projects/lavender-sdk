package de.honoka.lavender.lavsource.core.android.provider

import cn.hutool.json.JSON
import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import de.honoka.lavender.lavsource.core.android.util.LavenderApplicationUtils
import de.honoka.sdk.util.android.basic.call
import de.honoka.sdk.util.android.basic.global
import de.honoka.sdk.util.android.basic.toFunctionArgs
import de.honoka.sdk.util.android.provider.BaseContentProvider
import de.honoka.sdk.util.kotlin.basic.tryCastOrNull
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.typeOf

abstract class AbstractLavsourceProvider : BaseContentProvider() {

    companion object {

        private var businessList: List<Any>? = null

        private lateinit var businessMap: Map<String, Any>

        private fun initBusinessMap() {
            val map = HashMap<String, Any>()
            businessList!!.forEach {
                val classes = HashSet<Class<*>>()
                classes.addAll(it.javaClass.interfaces)
                val superClass = it.javaClass.superclass
                classes.add(if(superClass == Any::class.java) it.javaClass else superClass)
                classes.forEach { c ->
                    map[c.name] = it
                }
            }
            businessMap = map
        }
    }

    protected abstract val applicationUtils: LavenderApplicationUtils

    override fun onCreate(): Boolean {
        applicationUtils.initApplication(context!!, false)
        checkOrInitBusinessMap()
        return super.onCreate()
    }

    protected abstract fun newBusinessList(): List<Any>

    private fun checkOrInitBusinessMap() {
        businessList?.let { return }
        synchronized(this) {
            businessList?.let { return }
            businessList = newBusinessList()
            initBusinessMap()
        }
    }

    override fun call(method: String?, args: JSON?): Any? {
        args as JSONObject
        val request = args.toBean(LavsourceProviderRequest::class.java)
        val business = businessMap[request.className].also {
            it ?: error("Unknown class name: ${request.className}")
        }
        val function = business!!::class.declaredFunctions.first { it.name == request.function }
        return function.call(business, *request.args.toFunctionArgs(function))
    }
}

data class LavsourceProviderRequest(

    var className: String? = null,

    var function: String? = null,

    var args: JSONArray = JSONArray()
)

@Suppress("UNCHECKED_CAST", "RemoveExplicitTypeArguments")
fun <T> callLavsourceProvider(
    packageName: String, businessFunction: KFunction<*>, args: Iterable<Any?>? = null
): T {
    val request = LavsourceProviderRequest().apply {
        className = businessFunction.javaMethod!!.declaringClass.run {
            if(interfaces.isEmpty()) name else interfaces[0].name
        }
        function = businessFunction.name
        args?.let {
            this.args = JSONArray(args, false)
        }
    }
    val result = global.contentResolver.call(
        "${packageName}.provider.LavsourceProvider",
        args = request
    )
    businessFunction.returnType.run {
        if(isSubtypeOf(typeOf<Unit>())) {
            return null as T
        }
        return result.tryCastOrNull<T>(this) as T
    }
}
