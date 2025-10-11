package de.honoka.lavender.lavsource.core.android.util

import cn.hutool.core.io.IoUtil
import cn.hutool.http.HttpResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

object VideoUtils {

    suspend fun forwardVideoStream(originalResponse: HttpResponse, call: ApplicationCall, range: String?) {
        val videoStream = originalResponse.bodyStream().buffered()
        call.response.run {
            val status = if(range == null) {
                header(HttpHeaders.AcceptRanges, "bytes")
                HttpStatusCode.OK
            } else {
                HttpStatusCode.PartialContent
            }
            val contentType = originalResponse.header(HttpHeaders.ContentType)
            header(
                HttpHeaders.ContentLength,
                originalResponse.header(HttpHeaders.ContentLength)
            )
            header(
                HttpHeaders.ContentRange,
                originalResponse.header(HttpHeaders.ContentRange)
            )
            call.respondOutputStream(ContentType.parse(contentType), status) {
                runCatching {
                    IoUtil.copy(videoStream, this)
                }.getOrElse {
                    runCatching {
                        originalResponse.close()
                        flush()
                    }
                }
            }
        }
    }
}
