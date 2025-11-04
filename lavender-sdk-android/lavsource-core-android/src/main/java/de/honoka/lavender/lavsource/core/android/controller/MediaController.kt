package de.honoka.lavender.lavsource.core.android.controller

import de.honoka.lavender.api.business.MediaBusiness
import de.honoka.lavender.lavsource.core.android.util.VideoUtils
import de.honoka.sdk.util.android.server.RoutingDefinition
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class MediaController(private val mediaBusiness: MediaBusiness) {

    val routingDefinition: RoutingDefinition = {
        get("/image/proxy", imageProxy)
        get("/video/stream", videoStream)
    }

    private val imageProxy: RoutingHandler = {
        val url = call.parameters["url"]!!
        mediaBusiness.getImageResponse(url).use {
            call.respondBytes(
                it.bodyBytes(),
                ContentType.parse(it.header(HttpHeaders.ContentType))
            )
        }
    }

    private val videoStream: RoutingHandler = {
        val url = call.parameters["url"]!!
        val range = call.request.header(HttpHeaders.Range)
        mediaBusiness.getVideoResponse(url, range).use {
            val range = call.request.header(HttpHeaders.Range)
            VideoUtils.forwardVideoStream(it, call, range)
        }
    }
}
