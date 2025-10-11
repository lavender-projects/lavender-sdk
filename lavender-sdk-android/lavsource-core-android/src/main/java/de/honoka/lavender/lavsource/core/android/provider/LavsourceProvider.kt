package de.honoka.lavender.lavsource.core.android.provider

import cn.hutool.json.JSON
import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import de.honoka.lavender.lavsource.core.android.util.LavsourceApplicationUtils
import de.honoka.sdk.util.android.basic.BaseContentProvider
import de.honoka.sdk.util.android.basic.global
import de.honoka.sdk.util.android.basic.toFunctionArgs
import de.honoka.sdk.util.android.basic.typedCall
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.javaMethod

abstract class AbstractLavsourceProvider : BaseContentProvider() {

    companion object {

        private var businessList: List<Any>? = null

        private lateinit var businessMap: Map<String, Any>

        private fun initBusinessMap() {
            val map = HashMap<String, Any>()
            businessList!!.forEach {
                val classes = ArrayList<Class<*>>()
                classes.addAll(it.javaClass.interfaces)
                val superClass = it.javaClass.superclass
                classes.add(if(superClass == Any::class.java) it.javaClass else superClass)
                classes.forEach { clazz ->
                    map[clazz.simpleName] = it
                }
            }
            businessMap = map
        }
    }

    protected abstract val applicationUtils: LavsourceApplicationUtils

    override fun onCreate(): Boolean {
        applicationUtils.initApplication(context!!)
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

inline fun <reified T : Any> callLavsourceProvider(
    packageName: String, businessFunction: KFunction<*>, args: Iterable<Any?>? = null
): T {
    val businessJavaMethod = businessFunction.javaMethod!!
    val request = LavsourceProviderRequest().apply {
        className = businessJavaMethod.declaringClass.simpleName
        function = businessFunction.name
        args?.let {
            this.args = JSONArray(args, false)
        }
    }
    /*
     * 若实化泛型T中含有嵌套泛型，则result的类型只能保证是T的顶级类型或T的顶级类型的子类，
     * 当泛型T的嵌套泛型出现在泛型T的字段中时，无法保证result对象中的对应字段的实际类型一定是
     * 泛型T的嵌套泛型或该嵌套泛型的子类。
     *
     * 比如，有一个类型：Entity<U>，Entity类中定义了一个类型为泛型U的字段：field1，则当该方法中
     * 的T类型为Entity<Other>（假设Other是另一个不含泛型的实体类）时，只能保证result一定是Entity
     * 类型，无法保证result对象中的field1字段一定是Other类型，其具体类型与ContentProvider的call
     * 方法在调用其他应用后，得到的JSON数据中的field1字段的类型有关，只可能是基本数据类型、JSONObject或
     * JSONArray。
     */
    val result = global.contentResolver.typedCall<T>(
        "${packageName}.provider.LavsourceProvider", args = request
    )
    val methodReturnType = businessJavaMethod.genericReturnType
    if(result !is MutableCollection<*> || methodReturnType !is ParameterizedType) {
        return result
    }
    @Suppress("UNCHECKED_CAST")
    result as MutableCollection<Any?>
    val itemClass = methodReturnType.actualTypeArguments[0] as Class<*>
    val typedItems = ArrayList<Any?>().apply {
        result.forEach {
            add(if(it is JSONObject) it.toBean(itemClass) else it)
        }
    }
    //不可直接将转换为实体类对象后的item添加到hutool的JSONArray中，否则会被转回JSONObject
    if(result !is JSONArray) {
        result.run {
            clear()
            addAll(typedItems)
        }
        return result
    }
    (methodReturnType.rawType as Class<*>).let {
        if(List::class.java.isAssignableFrom(it)) {
            return typedItems as T
        }
        if(Set::class.java.isAssignableFrom(it)) {
            return HashSet(typedItems) as T
        }
    }
    error("Unknown method return type: $methodReturnType")
}
