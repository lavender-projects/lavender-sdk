package de.honoka.lavender.lavsource.starter.util

import de.honoka.lavender.api.util.AbstractLavsourceUtils
import de.honoka.lavender.lavsource.starter.config.ServerProperties
import de.honoka.sdk.spring.starter.core.context.springBean
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object LavsourceUtils : AbstractLavsourceUtils {

    private val serverProperties by lazy { ServerProperties::class.springBean }

    override fun getProxiedImageUrl(url: String): String {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        return "${serverProperties.serverAccessUrlPrefix}/platform/test/image/proxy?url=$encodedUrl"
    }

    override fun getProxiedMediaStreamUrl(url: String): String {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        return "${serverProperties.serverAccessUrlPrefix}/video/stream?url=$encodedUrl"
    }
}
