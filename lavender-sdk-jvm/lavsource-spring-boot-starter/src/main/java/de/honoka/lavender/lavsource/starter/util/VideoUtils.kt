package de.honoka.lavender.lavsource.starter.util

import cn.hutool.core.io.IoUtil
import cn.hutool.http.HttpResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

object VideoUtils {

    fun forwardVideoStream(originalResponse: HttpResponse, response: HttpServletResponse, range: String?) {
        val videoStream = originalResponse.bodyStream().buffered()
        response.run {
            contentType = originalResponse.header(HttpHeaders.CONTENT_TYPE)
            addHeader(
                HttpHeaders.CONTENT_LENGTH,
                originalResponse.header(HttpHeaders.CONTENT_LENGTH)
            )
            status = if(range == null) {
                addHeader(HttpHeaders.ACCEPT_RANGES, "bytes")
                HttpStatus.OK.value()
            } else {
                HttpStatus.PARTIAL_CONTENT.value()
            }
            addHeader(
                HttpHeaders.CONTENT_RANGE,
                originalResponse.header(HttpHeaders.CONTENT_RANGE)
            )
            runCatching {
                IoUtil.copy(videoStream, outputStream)
            }
            outputStream.flush()
        }
    }
}
