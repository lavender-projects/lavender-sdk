package de.honoka.lavender.api.util

import cn.hutool.http.HttpRequest
import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONObject
import de.honoka.sdk.util.kotlin.basic.RemoteInvokeException
import de.honoka.sdk.util.kotlin.basic.tryCastOrNull
import de.honoka.sdk.util.kotlin.reflect.setInstanceProp
import de.honoka.sdk.util.kotlin.text.toJsonObject
import de.honoka.sdk.util.web.ApiResponse
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.typeOf

abstract class BusinessStub {

    protected lateinit var stubId: String

    open val pathPrefix = ""

    protected fun url(path: String): String = "$stubId$pathPrefix$path"

    protected inline fun <reified T> request(request: HttpRequest): T? {
        val response = request.execute().body()
        if(response.isBlank()) return null
        val resObj = response.toJsonObject().toBean<ApiResponse<Any?>>(ApiResponse::class.java)
        if(!resObj.status) {
            val e = RemoteInvokeException(
                resObj.msg,
                (resObj.data as JSONObject).getStr("stackTrace")
            )
            LavsourceUtils.logRemoteInvokeException(e)
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
