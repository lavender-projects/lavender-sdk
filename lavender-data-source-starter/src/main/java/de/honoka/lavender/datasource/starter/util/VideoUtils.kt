package de.honoka.lavender.datasource.starter.util

import cn.hutool.core.io.IoUtil
import cn.hutool.http.HttpResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletResponse

object VideoUtils {

    fun forwardVideoStream(originalResponse: HttpResponse, response: HttpServletResponse, range: String?) {
        val videoStream = originalResponse.bodyStream().buffered()
        response.run {
            contentType = originalResponse.header(HttpHeaders.CONTENT_TYPE)
            addHeader(HttpHeaders.CONTENT_LENGTH, originalResponse.header(HttpHeaders.CONTENT_LENGTH))
            status = if(range == null) {
                addHeader(HttpHeaders.ACCEPT_RANGES, "bytes")
                HttpStatus.OK.value()
            } else {
                HttpStatus.PARTIAL_CONTENT.value()
            }
            addHeader(HttpHeaders.CONTENT_RANGE, originalResponse.header(HttpHeaders.CONTENT_RANGE))
            try {
                IoUtil.copy(videoStream, outputStream)
            } catch(t: Throwable) {
                originalResponse.close()
                outputStream.flush()
            }
        }
    }
}