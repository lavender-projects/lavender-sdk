package de.honoka.lavender.android.lavsource.sdk.provider

import android.app.Application
import cn.hutool.json.JSON
import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import de.honoka.lavender.android.lavsource.sdk.util.ApplicationUtils
import de.honoka.sdk.util.android.common.BaseContentProvider
import de.honoka.sdk.util.android.common.toMethodArgs
import java.lang.reflect.Method

abstract class AbstractLavsourceProvider : BaseContentProvider() {

    companion object {

        private var businessList: List<Any>? = null

        private lateinit var businessMap: Map<String, Any>

        private fun initBusinessMap() {
            businessMap = HashMap<String, Any>().also { map ->
                businessList!!.forEach {
                    ArrayList<Class<*>>().apply {
                        addAll(it.javaClass.interfaces)
                        val superClass = it.javaClass.superclass
                        add(if(superClass == Any::class.java) it.javaClass else superClass)
                    }.forEach { clazz ->
                        map[clazz.simpleName] = it
                    }
                }
            }
        }
    }

    protected abstract val applicationUtilsAbstractPart: ApplicationUtils.AbstractPart

    override fun onCreate(): Boolean = super.onCreate().apply {
        ApplicationUtils.initAbstractPart(applicationUtilsAbstractPart)
        ApplicationUtils.initApplication(context!!.applicationContext as Application)
        checkOrInitBusinessMap()
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
            it ?: throw Exception("Unknown class name: ${request.className}")
        }
        val methodObj: Method = business!!.javaClass.declaredMethods.run {
            forEach {
                if(it.name == request.method) return@run it
            }
            throw Exception("Unknown method name \"${request.method}\" of class: ${request.className}")
        }
        return methodObj.invoke(business, *request.args.toMethodArgs(methodObj))
    }
}

data class LavsourceProviderRequest(

    var className: String? = null,

    var method: String? = null,

    var args: JSONArray = JSONArray()
)