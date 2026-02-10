package de.honoka.lavender.lavsource.core.android.controller

import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.sdk.util.android.web.server.ktor.GetMapping
import de.honoka.sdk.util.android.web.server.ktor.RequestMapping
import de.honoka.sdk.util.android.web.server.ktor.RestController
import de.honoka.sdk.util.kotlin.web.ApiResponse
import de.honoka.sdk.util.kotlin.web.toApiResponse
import io.ktor.server.routing.*

@RequestMapping("/video")
@RestController
class VideoController(private val videoBusiness: VideoBusiness) {

    @GetMapping("/recommended")
    fun recommendedVideoList(): ApiResponse<List<VideoItem>> =
        videoBusiness.getRecommendedVideoList().toApiResponse()

    @GetMapping("/details")
    fun videoDetails(call: RoutingCall): ApiResponse<VideoDetails> {
        val id = call.parameters["id"]!!
        return videoBusiness.getVideoDetails(id).toApiResponse()
    }

    @GetMapping("/comment/list")
    fun commentList(call: RoutingCall): ApiResponse<CommentList> {
        val videoId = call.parameters["videoId"]!!
        val sortBy = call.parameters["sortBy"] ?: "like_count"
        val page = call.parameters["page"]?.toInt() ?: 1
        return videoBusiness.getCommentList(videoId, sortBy, page).toApiResponse()
    }

    @GetMapping("/comment/reply/list")
    fun commentReplyList(call: RoutingCall): ApiResponse<CommentList> {
        val videoId = call.parameters["videoId"]!!
        val commentId = call.parameters["commentId"]!!
        val page = call.parameters["page"]?.toInt() ?: 1
        return videoBusiness.getCommentReplyList(videoId, commentId, page).toApiResponse()
    }

    @GetMapping("/episode/list")
    fun episodeList(call: RoutingCall): ApiResponse<List<VideoEpisodeInfo>> {
        val videoId = call.parameters["videoId"]!!
        return videoBusiness.getEpisodeList(videoId).toApiResponse()
    }

    @GetMapping("/stream/urlList")
    fun streamUrlList(call: RoutingCall): ApiResponse<List<VideoStreamInfo>> {
        val videoId = call.parameters["videoId"]!!
        val episodeId = call.parameters["episodeId"]!!
        return videoBusiness.getStreamUrlList(videoId, episodeId).toApiResponse()
    }

    @GetMapping("/danmaku/list")
    fun danmakuList(call: RoutingCall): ApiResponse<List<DanmakuInfo>> {
        val episodeId = call.parameters["episodeId"]!!
        return videoBusiness.getDanmakuList(episodeId).toApiResponse()
    }
}
