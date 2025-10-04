package de.honoka.lavender.lavsource.starter.util

import de.honoka.lavender.api.util.AbstractLavsourceUtils
import de.honoka.lavender.lavsource.starter.common.PropertiesHolder
import de.honoka.sdk.spring.starter.core.context.springBean
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object LavsourceUtils : AbstractLavsourceUtils {

    private val propertiesHolder by lazy { PropertiesHolder::class.springBean }

    override fun getProxiedImageUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        "${propertiesHolder.serverAccessUrlPrefix}/platform/test/image/proxy?url=$encodedUrl"
    }

    override fun getProxiedMediaStreamUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        "${propertiesHolder.serverAccessUrlPrefix}/video/stream?url=$encodedUrl"
    }
}
