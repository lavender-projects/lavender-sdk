package de.honoka.lavender.lavsource.starter.util

import de.honoka.lavender.api.util.LavsourceUtils
import de.honoka.lavender.lavsource.starter.config.ServerProperties
import org.springframework.stereotype.Component
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class LavsourceUtilsImpl(private val serverProperties: ServerProperties) : LavsourceUtils.AbstractPart {

    init {
        LavsourceUtils.abstractPart = this
    }

    override fun getProxiedImageUrl(url: String): String {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        return "${serverProperties.serverAccessUrlPrefix}/image/proxy?url=$encodedUrl"
    }

    override fun getProxiedMediaStreamUrl(url: String): String {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        return "${serverProperties.serverAccessUrlPrefix}/video/stream?url=$encodedUrl"
    }
}
