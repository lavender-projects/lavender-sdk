package de.honoka.lavender.lavsource.starter.controller

import de.honoka.lavender.api.business.MediaBusiness
import de.honoka.lavender.lavsource.starter.util.VideoUtils
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MediaController(private val mediaBusiness: MediaBusiness) {

    @GetMapping("/image/proxy")
    fun imageProxy(@RequestParam url: String, response: HttpServletResponse) {
        mediaBusiness.getImageResponse(url).use {
            response.run {
                contentType = it.header(HttpHeaders.CONTENT_TYPE)
                outputStream.write(it.bodyBytes())
            }
        }
    }

    @GetMapping("/video/stream")
    fun videoStream(
        @RequestParam url: String,
        @RequestHeader(HttpHeaders.RANGE, required = false) range: String?,
        response: HttpServletResponse
    ) {
        mediaBusiness.getVideoResponse(url, range).use {
            VideoUtils.forwardVideoStream(it, response, range)
        }
    }
}
