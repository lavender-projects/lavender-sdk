package de.honoka.lavender.android.lavsource.sdk.util

import de.honoka.lavender.api.util.AbstractLavsourceUtils
import de.honoka.sdk.util.android.server.HttpServer
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object LavsourceUtils : AbstractLavsourceUtils {

    override fun getProxiedImageUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        HttpServer.Variables.getUrlByPath("/image/proxy?url=$encodedUrl")
    }

    override fun getProxiedMediaStreamUrl(url: String): String = run {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        HttpServer.Variables.getUrlByPath("/video/stream?url=$encodedUrl")
    }
}
