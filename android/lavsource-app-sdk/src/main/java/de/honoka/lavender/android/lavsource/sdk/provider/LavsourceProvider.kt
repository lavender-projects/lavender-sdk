package de.honoka.lavender.android.lavsource.sdk.provider

import cn.hutool.json.JSON
import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import de.honoka.sdk.util.android.basic.AbstractApplicationUtils
import de.honoka.sdk.util.android.basic.BaseContentProvider
import de.honoka.sdk.util.android.basic.toFunctionArgs
import kotlin.reflect.full.declaredFunctions

abstract class AbstractLavsourceProvider : BaseContentProvider() {

    companion object {

        private var businessList: List<Any>? = null

        private lateinit var businessMap: MutableMap<String, Any>

        private fun initBusinessMap() {
            businessMap = HashMap()
            businessList!!.forEach {
                val classes = ArrayList<Class<*>>()
                classes.addAll(it.javaClass.interfaces)
                val superClass = it.javaClass.superclass
                classes.add(if(superClass == Any::class.java) it.javaClass else superClass)
                classes.forEach { clazz ->
                    businessMap[clazz.simpleName] = it
                }
            }
        }
    }

    protected abstract val applicationUtils: AbstractApplicationUtils

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
