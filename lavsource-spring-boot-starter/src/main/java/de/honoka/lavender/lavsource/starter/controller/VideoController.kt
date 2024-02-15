package de.honoka.lavender.lavsource.starter.controller

import de.honoka.lavender.api.data.*
import de.honoka.lavender.lavsource.starter.service.VideoService
import de.honoka.lavender.lavsource.starter.util.VideoUtils
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RequestMapping("/video")
@RestController
class VideoController(
    private val videoService: VideoService
) {

    @GetMapping("/recommended")
    fun recommendedVideoList(): ApiResponse<List<RecommendedVideoItem>> = ApiResponse.success(
        videoService.getRecommendedVideoList()
    )

    @GetMapping("/details")
    fun videoDetails(@RequestParam id: String): ApiResponse<VideoDetails> = ApiResponse.success(
        videoService.getVideoDetails(id)
    )

    @GetMapping("/comment/list")
    fun commentList(
        @RequestParam videoId: String,
        @RequestParam(required = false, defaultValue = "like_count") sortBy: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = ApiResponse.success(videoService.getCommentList(videoId, sortBy, page))

    @GetMapping("/comment/reply/list")
    fun commentReplyList(
        @RequestParam videoId: String,
        @RequestParam commentId: Long,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ApiResponse<CommentList> = ApiResponse.success(videoService.getCommentReplyList(videoId, commentId, page))

    @GetMapping("/episode/list")
    fun episodeList(@RequestParam videoId: String): ApiResponse<List<VideoEpisodeInfo>> = ApiResponse.success(
        videoService.getEpisodeList(videoId)
    )

    @GetMapping("/stream/urlList")
    fun streamUrlList(
        @RequestParam videoId: String,
        @RequestParam episodeId: Long
    ): ApiResponse<List<VideoStreamInfo>> = ApiResponse.success(videoService.getStreamUrlList(videoId, episodeId))

    @GetMapping("/stream")
    fun stream(
        @RequestParam url: String,
        @RequestHeader(HttpHeaders.RANGE, required = false) range: String?,
        response: HttpServletResponse
    ) = VideoUtils.forwardVideoStream(videoService.getVideoStreamResponse(url, range), response, range)

    @GetMapping("/danmaku/list")
    fun danmakuList(@RequestParam episodeId: Long): ApiResponse<List<DanmakuInfo>> = ApiResponse.success(
        videoService.getDanmakuList(episodeId)
    )
}