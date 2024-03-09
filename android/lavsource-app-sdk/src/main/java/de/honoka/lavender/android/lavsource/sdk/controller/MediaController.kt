package de.honoka.lavender.android.lavsource.sdk.controller

import cn.hutool.http.HttpResponse
import de.honoka.lavender.android.lavsource.sdk.util.VideoUtils
import de.honoka.sdk.util.android.server.RoutingDefinition
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

@Suppress("MemberVisibilityCanBePrivate")
abstract class AbstractMediaController {

    val routingDefinition: RoutingDefinition by lazy {
        {
            get("/image/proxy", imageProxy)
            get("/video/stream", videoStream)
        }
    }

    private val imageProxy: PipelineInterceptor<Unit, ApplicationCall> = {
        val originalRes = getImageResponse(call)
        call.respondBytes(originalRes.bodyBytes(), ContentType.parse(originalRes.header(HttpHeaders.ContentType)))
    }

    private val videoStream: PipelineInterceptor<Unit, ApplicationCall> = {
        val range = call.request.header(HttpHeaders.Range)
        VideoUtils.forwardVideoStream(getVideoResponse(call), call, range)
    }

    protected abstract fun getImageResponse(call: ApplicationCall): HttpResponse

    protected abstract fun getVideoResponse(call: ApplicationCall): HttpResponse
}