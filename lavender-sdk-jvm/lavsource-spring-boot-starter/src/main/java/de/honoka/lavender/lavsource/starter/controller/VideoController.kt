package de.honoka.lavender.lavsource.starter.controller

import de.honoka.lavender.api.business.MediaBusiness
import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.lavender.lavsource.starter.util.VideoUtils
import de.honoka.sdk.util.web.ApiResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RequestMapping("/video")
@RestController
class VideoController(
    private val videoBusiness: VideoBusiness,
    private val mediaBusiness: MediaBusiness
) {

    @GetMapping("/recommended")
    fun recommendedVideoList(): ApiResponse<List<RecommendedVideoItem>> = run {
        ApiResponse.success(videoBusiness.getRecommendedVideoList())
    }

    @GetMapping("/details")
    fun videoDetails(@RequestParam id: String): ApiResponse<VideoDetails> = run {
        ApiResponse.success(videoBusiness.getVideoDetails(id))
    }

    @GetMapping("/comment/list")
    fun commentList(
        @RequestParam videoId: String,
        @RequestParam(required = false, defaultValue = "like_count") sortBy: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = run {
        ApiResponse.success(videoBusiness.getCommentList(videoId, sortBy, page))
    }

    @GetMapping("/comment/reply/list")
    fun commentReplyList(
        @RequestParam videoId: String,
        @RequestParam commentId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = run {
        ApiResponse.success(videoBusiness.getCommentReplyList(videoId, commentId, page))
    }

    @GetMapping("/episode/list")
    fun episodeList(@RequestParam videoId: String): ApiResponse<List<VideoEpisodeInfo>> = run {
        ApiResponse.success(videoBusiness.getEpisodeList(videoId))
    }

    @GetMapping("/stream/urlList")
    fun streamUrlList(
        @RequestParam videoId: String,
        @RequestParam episodeId: String
    ): ApiResponse<List<VideoStreamInfo>> = run {
        ApiResponse.success(videoBusiness.getStreamUrlList(videoId, episodeId))
    }

    @GetMapping("/stream")
    fun stream(
        @RequestParam url: String,
        @RequestHeader(HttpHeaders.RANGE, required = false) range: String?,
        response: HttpServletResponse
    ) {
        mediaBusiness.getVideoResponse(url, range).use {
            VideoUtils.forwardVideoStream(it, response, range)
        }
    }

    @GetMapping("/danmaku/list")
    fun danmakuList(@RequestParam episodeId: String): ApiResponse<List<DanmakuInfo>> = run {
        ApiResponse.success(videoBusiness.getDanmakuList(episodeId))
    }
}
