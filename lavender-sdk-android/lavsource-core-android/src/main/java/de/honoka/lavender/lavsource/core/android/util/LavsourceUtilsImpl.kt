package de.honoka.lavender.lavsource.core.android.util

import de.honoka.lavender.api.util.LavsourceUtils
import de.honoka.sdk.util.android.server.HttpServer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object LavsourceUtilsImpl : LavsourceUtils.AbstractPart {

    override fun getProxiedImageUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        HttpServer.Variables.getUrlByPath("/image/proxy?url=$encodedUrl")
    }

    override fun getProxiedMediaStreamUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        HttpServer.Variables.getUrlByPath("/video/stream?url=$encodedUrl")
    }
}
