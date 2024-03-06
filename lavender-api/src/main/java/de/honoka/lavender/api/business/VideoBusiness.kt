package de.honoka.lavender.api.business

import cn.hutool.http.HttpResponse
import de.honoka.lavender.api.data.*

interface VideoBusiness {

    fun getRecommendedVideoList(): List<RecommendedVideoItem>

    fun getVideoDetails(id: String): VideoDetails

    fun getCommentList(videoId: String, sortBy: String, page: Int): CommentList

    fun getCommentReplyList(videoId: String, commentId: Long, page: Int): CommentList

    fun getEpisodeList(videoId: String): List<VideoEpisodeInfo>

    fun getStreamUrlList(videoId: String, episodeId: Long): List<VideoStreamInfo>

    fun getVideoStreamResponse(url: String, range: String?): HttpResponse

    fun getDanmakuList(episodeId: Long): List<DanmakuInfo>
}