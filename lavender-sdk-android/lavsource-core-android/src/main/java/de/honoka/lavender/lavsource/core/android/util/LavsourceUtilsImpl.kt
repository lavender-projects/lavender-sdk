package de.honoka.lavender.lavsource.core.android.util

import de.honoka.lavender.api.util.LavsourceUtils
import de.honoka.sdk.util.android.server.HttpServerService
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object LavsourceUtilsImpl : LavsourceUtils.AbstractPart {

    override fun getProxiedImageUrl(url: String): String {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        return HttpServerService.getUrlByPath("/image/proxy?url=$encodedUrl")
    }

    override fun getProxiedMediaStreamUrl(url: String): String {
        val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.name())
        return HttpServerService.getUrlByPath("/video/stream?url=$encodedUrl")
    }
}
