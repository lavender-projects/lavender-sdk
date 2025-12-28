package de.honoka.lavender.lavsource.core.android.controller

import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.sdk.util.android.server.ktor.GetMapping
import de.honoka.sdk.util.android.server.ktor.RequestMapping
import de.honoka.sdk.util.android.server.ktor.RestController
import de.honoka.sdk.util.web.ApiResponse
import io.ktor.server.routing.*

@RequestMapping("/video")
@RestController
class VideoController(private val videoBusiness: VideoBusiness) {

    @GetMapping("/recommended")
    fun recommendedVideoList(): ApiResponse<List<VideoItem>> = run {
        ApiResponse.success(videoBusiness.getRecommendedVideoList())
    }

    @GetMapping("/details")
    fun videoDetails(call: RoutingCall): ApiResponse<VideoDetails> {
        val id = call.parameters["id"]!!
        return ApiResponse.success(videoBusiness.getVideoDetails(id))
    }

    @GetMapping("/comment/list")
    fun commentList(call: RoutingCall): ApiResponse<CommentList> {
        val videoId = call.parameters["videoId"]!!
        val sortBy = call.parameters["sortBy"] ?: "like_count"
        val page = call.parameters["page"]?.toInt() ?: 1
        return ApiResponse.success(videoBusiness.getCommentList(videoId, sortBy, page))
    }

    @GetMapping("/comment/reply/list")
    fun commentReplyList(call: RoutingCall): ApiResponse<CommentList> {
        val videoId = call.parameters["videoId"]!!
        val commentId = call.parameters["commentId"]!!
        val page = call.parameters["page"]?.toInt() ?: 1
        return ApiResponse.success(videoBusiness.getCommentReplyList(videoId, commentId, page))
    }

    @GetMapping("/episode/list")
    fun episodeList(call: RoutingCall): ApiResponse<List<VideoEpisodeInfo>> {
        val videoId = call.parameters["videoId"]!!
        return ApiResponse.success(videoBusiness.getEpisodeList(videoId))
    }

    @GetMapping("/stream/urlList")
    fun streamUrlList(call: RoutingCall): ApiResponse<List<VideoStreamInfo>> {
        val videoId = call.parameters["videoId"]!!
        val episodeId = call.parameters["episodeId"]!!
        return ApiResponse.success(videoBusiness.getStreamUrlList(videoId, episodeId))
    }

    @GetMapping("/danmaku/list")
    fun danmakuList(call: RoutingCall): ApiResponse<List<DanmakuInfo>> {
        val episodeId = call.parameters["episodeId"]!!
        return ApiResponse.success(videoBusiness.getDanmakuList(episodeId))
    }
}
