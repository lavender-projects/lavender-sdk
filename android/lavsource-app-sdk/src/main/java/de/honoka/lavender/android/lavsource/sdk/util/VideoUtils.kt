package de.honoka.lavender.android.lavsource.sdk.util

import cn.hutool.core.io.IoUtil
import cn.hutool.http.HttpResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

object VideoUtils {

    suspend fun forwardVideoStream(originalResponse: HttpResponse, ktorCall: ApplicationCall, range: String?) {
        val videoStream = originalResponse.bodyStream().buffered()
        ktorCall.response.run {
            val status = if(range == null) {
                header(HttpHeaders.AcceptRanges, "bytes")
                HttpStatusCode.OK
            } else {
                HttpStatusCode.PartialContent
            }
            val contentType = originalResponse.header(HttpHeaders.ContentType)
            header(HttpHeaders.ContentLength, originalResponse.header(HttpHeaders.ContentLength))
            header(HttpHeaders.ContentRange, originalResponse.header(HttpHeaders.ContentRange))
            ktorCall.respondOutputStream(ContentType.parse(contentType), status) {
                try {
                    IoUtil.copy(videoStream, this)
                } catch(t: Throwable) {
                    runCatching {
                        originalResponse.close()
                        flush()
                    }
                }
            }
        }
    }
}