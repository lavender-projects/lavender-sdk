package de.honoka.lavender.lavsource.starter.controller

import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.sdk.util.kotlin.web.ApiResponse
import de.honoka.sdk.util.kotlin.web.toApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/video")
@RestController
class VideoController(private val videoBusiness: VideoBusiness) {

    @GetMapping("/recommended")
    fun recommendedVideoList(): ApiResponse<List<VideoItem>> =
        videoBusiness.getRecommendedVideoList().toApiResponse()

    @GetMapping("/details")
    fun videoDetails(@RequestParam id: String): ApiResponse<VideoDetails> =
        videoBusiness.getVideoDetails(id).toApiResponse()

    @GetMapping("/comment/list")
    fun commentList(
        @RequestParam videoId: String,
        @RequestParam(required = false, defaultValue = "like_count") sortBy: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = videoBusiness.getCommentList(videoId, sortBy, page).toApiResponse()

    @GetMapping("/comment/reply/list")
    fun commentReplyList(
        @RequestParam videoId: String,
        @RequestParam commentId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = videoBusiness.getCommentReplyList(videoId, commentId, page).toApiResponse()

    @GetMapping("/episode/list")
    fun episodeList(@RequestParam videoId: String): ApiResponse<List<VideoEpisodeInfo>> =
        videoBusiness.getEpisodeList(videoId).toApiResponse()

    @GetMapping("/stream/urlList")
    fun streamUrlList(
        @RequestParam videoId: String,
        @RequestParam episodeId: String
    ): ApiResponse<List<VideoStreamInfo>> = videoBusiness.getStreamUrlList(videoId, episodeId).toApiResponse()

    @GetMapping("/danmaku/list")
    fun danmakuList(@RequestParam episodeId: String): ApiResponse<List<DanmakuInfo>> =
        videoBusiness.getDanmakuList(episodeId).toApiResponse()
}
