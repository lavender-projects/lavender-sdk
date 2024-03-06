package de.honoka.lavender.lavsource.starter.controller

import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.lavender.lavsource.starter.util.VideoUtils
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RequestMapping("/video")
@RestController
class VideoController(private val videoBusiness: VideoBusiness) {

    @GetMapping("/recommended")
    fun recommendedVideoList(): ApiResponse<List<RecommendedVideoItem>> = ApiResponse.success(
        videoBusiness.getRecommendedVideoList()
    )

    @GetMapping("/details")
    fun videoDetails(@RequestParam id: String): ApiResponse<VideoDetails> = ApiResponse.success(
        videoBusiness.getVideoDetails(id)
    )

    @GetMapping("/comment/list")
    fun commentList(
        @RequestParam videoId: String,
        @RequestParam(required = false, defaultValue = "like_count") sortBy: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = ApiResponse.success(videoBusiness.getCommentList(videoId, sortBy, page))

    @GetMapping("/comment/reply/list")
    fun commentReplyList(
        @RequestParam videoId: String,
        @RequestParam commentId: Long,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = ApiResponse.success(videoBusiness.getCommentReplyList(videoId, commentId, page))

    @GetMapping("/episode/list")
    fun episodeList(@RequestParam videoId: String): ApiResponse<List<VideoEpisodeInfo>> = ApiResponse.success(
        videoBusiness.getEpisodeList(videoId)
    )

    @GetMapping("/stream/urlList")
    fun streamUrlList(
        @RequestParam videoId: String,
        @RequestParam episodeId: Long
    ): ApiResponse<List<VideoStreamInfo>> = ApiResponse.success(videoBusiness.getStreamUrlList(videoId, episodeId))

    @GetMapping("/stream")
    fun stream(
        @RequestParam url: String,
        @RequestHeader(HttpHeaders.RANGE, required = false) range: String?,
        response: HttpServletResponse
    ) = VideoUtils.forwardVideoStream(videoBusiness.getVideoStreamResponse(url, range), response, range)

    @GetMapping("/danmaku/list")
    fun danmakuList(@RequestParam episodeId: Long): ApiResponse<List<DanmakuInfo>> = ApiResponse.success(
        videoBusiness.getDanmakuList(episodeId)
    )
}