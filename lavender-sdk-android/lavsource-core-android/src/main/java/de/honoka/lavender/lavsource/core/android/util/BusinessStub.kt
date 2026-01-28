package de.honoka.lavender.lavsource.core.android.util

import android.util.Log
import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONObject
import de.honoka.lavender.lavsource.core.android.business.BasicBusinessStub
import de.honoka.lavender.lavsource.core.android.provider.callLavsourceProvider
import de.honoka.sdk.util.kotlin.lang.tryBlock
import de.honoka.sdk.util.kotlin.lang.tryCastOrNull
import de.honoka.sdk.util.kotlin.reflect.setInstanceProp
import de.honoka.sdk.util.kotlin.text.toJsonObject
import de.honoka.sdk.util.kotlin.various.RemoteInvokeException
import de.honoka.sdk.util.web.ApiResponse
import java.net.ConnectException
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf

abstract class BusinessStub {

    protected lateinit var stubId: String
}

abstract class WebBusinessStub : BusinessStub() {

    open val pathPrefix = ""

    var androidPackageName: String? = null

    protected fun url(path: String): String = "$stubId$pathPrefix$path"

    protected inline fun <reified T> request(request: HttpRequest): T? {
        val response = tryBlock(3) {
            try {
                request.execute().body()
            } catch(ce: ConnectException) {
                androidPackageName ?: throw ce
                BasicBusinessStub[androidPackageName!!].restartHttpServerIfStopped()
                throw ce
            }
        }
        if(response.isBlank()) return null
        val resObj = response.toJsonObject().toBean<ApiResponse<Any?>>(ApiResponse::class.java)
        if(!resObj.success) {
            val e = RemoteInvokeException(
                resObj.msg,
                (resObj.data as JSONObject).getStr("stackTrace")
            )
            Log.e(javaClass.simpleName, e.stackTraceText)
            throw e
        }
        return resObj.data?.tryCastOrNull(typeOf<T>())
    }

    protected inline fun <reified T> getOrNull(path: String, block: HttpRequest.() -> Unit = {}): T? = run {
        request(HttpUtil.createGet(url(path)).apply(block))
    }

    protected inline fun <reified T> get(path: String, block: HttpRequest.() -> Unit = {}): T = run {
        getOrNull(path, block)!!
    }

    protected inline fun <reified T> postOrNull(path: String, block: HttpRequest.() -> Unit = {}): T? = run {
        request(HttpUtil.createPost(url(path)).apply(block))
    }

    protected inline fun <reified T> post(path: String, block: HttpRequest.() -> Unit = {}): T = run {
        postOrNull(path, block)!!
    }
}

abstract class AndroidBusinessStub : BusinessStub() {

    protected fun callProvider(businessFunction: KFunction<*>, args: Iterable<Any?>? = null): Any? = run {
        typedCallProvider(businessFunction, args)
    }

    protected fun <T> typedCallProvider(businessFunction: KFunction<*>, args: Iterable<Any?>? = null): T = run {
        callLavsourceProvider(stubId, businessFunction, args)
    }
}

abstract class BusinessStubCompanion<T : Any>(private val clazz: KClass<T>) {

    private val cache = ConcurrentHashMap<String, T>()

    operator fun get(stubId: String): T {
        cache[stubId]?.let { return it }
        synchronized(this) {
            val instance = clazz.primaryConstructor!!.call()
            clazz.setInstanceProp(instance, "stubId", stubId)
            cache[stubId] = instance
            return instance
        }
    }
}
