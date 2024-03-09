package de.honoka.lavender.android.lavsource.sdk.util

import cn.hutool.json.JSONArray
import cn.hutool.json.JSONObject
import de.honoka.lavender.android.lavsource.sdk.provider.LavsourceProviderRequest
import de.honoka.lavender.api.util.LavsourceUtils
import de.honoka.sdk.util.android.common.contentResolverTypedCall
import de.honoka.sdk.util.android.server.HttpServerVariables
import java.lang.reflect.ParameterizedType
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

object LavsourceUtilsAbstractPartImpl : LavsourceUtils.AbstractPart {

    override fun getProxiedImageUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        HttpServerVariables.getUrlByPath("/image/proxy?url=$encodedUrl")
    }

    override fun getProxiedMediaStreamUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        HttpServerVariables.getUrlByPath("/video/stream?url=$encodedUrl")
    }
}

inline fun <reified T> callLavsourceProvider(
    packageName: String, businessMethod: KFunction<*>, args: Iterable<Any?>? = null
): T {
    val businessJavaMethod = businessMethod.javaMethod!!
    val request = LavsourceProviderRequest().apply {
        className = businessJavaMethod.declaringClass.simpleName
        method = businessMethod.name
        args?.let { this.args = JSONArray(args, false) }
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
    val result = contentResolverTypedCall<T>("${packageName}.provider.LavsourceProvider", args = request)
    val methodReturnType = businessJavaMethod.genericReturnType
    if(result !is MutableCollection<*> || methodReturnType !is ParameterizedType) return result
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
        return result.apply {
            clear()
            addAll(typedItems)
        }
    }
    (methodReturnType.rawType as Class<*>).let {
        if(List::class.java.isAssignableFrom(it)) return typedItems as T
        if(Set::class.java.isAssignableFrom(it)) return HashSet(typedItems) as T
    }
    throw Exception("Unknown method return type: $methodReturnType")
}