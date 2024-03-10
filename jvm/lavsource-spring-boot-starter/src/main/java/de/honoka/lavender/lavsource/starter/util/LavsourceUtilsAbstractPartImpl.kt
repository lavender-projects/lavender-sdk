package de.honoka.lavender.lavsource.starter.util

import de.honoka.lavender.api.util.LavsourceUtils
import de.honoka.lavender.lavsource.starter.common.PropertiesHolder
import org.springframework.stereotype.Component
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class LavsourceUtilsAbstractPartImpl(private val propertiesHolder: PropertiesHolder) : LavsourceUtils.AbstractPart {

    override fun getProxiedImageUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        "${propertiesHolder.serverAccessUrlPrefix}/platform/bilibili/image/proxy?url=$encodedUrl"
    }

    override fun getProxiedMediaStreamUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        "${propertiesHolder.serverAccessUrlPrefix}/video/stream?url=$encodedUrl"
    }
}