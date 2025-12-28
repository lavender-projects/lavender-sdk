package de.honoka.lavender.lavsource.core.android.controller

import de.honoka.lavender.api.business.MediaBusiness
import de.honoka.lavender.lavsource.core.android.util.VideoUtils
import de.honoka.sdk.util.android.server.ktor.GetMapping
import de.honoka.sdk.util.android.server.ktor.RestController
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@RestController
class MediaController(private val mediaBusiness: MediaBusiness) {

    @GetMapping("/image/proxy")
    suspend fun imageProxy(call: RoutingCall) {
        val url = call.parameters["url"]!!
        mediaBusiness.getImageResponse(url).use {
            call.respondBytes(
                it.bodyBytes(),
                ContentType.parse(it.header(HttpHeaders.ContentType))
            )
        }
    }

    @GetMapping("/video/stream")
    suspend fun videoStream(call: RoutingCall) {
        val url = call.parameters["url"]!!
        val range = call.request.header(HttpHeaders.Range)
        mediaBusiness.getVideoResponse(url, range).use {
            VideoUtils.forwardVideoStream(it, call, range)
        }
    }
}
