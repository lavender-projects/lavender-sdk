package de.honoka.lavender.android.lavsource.sdk.business.stub

import cn.hutool.http.HttpResponse
import de.honoka.lavender.android.lavsource.sdk.util.callLavsourceProvider
import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*

class VideoBusinessStub(private val packageName: String) : VideoBusiness {

    override fun getRecommendedVideoList(): List<RecommendedVideoItem> = callLavsourceProvider(
        packageName, VideoBusiness::getRecommendedVideoList
    )

    override fun getVideoDetails(id: String): VideoDetails = callLavsourceProvider(
        packageName, VideoBusiness::getVideoDetails, listOf(id)
    )

    override fun getCommentList(videoId: String, sortBy: String, page: Int): CommentList = callLavsourceProvider(
        packageName, VideoBusiness::getCommentList, listOf(videoId, sortBy, page)
    )

    override fun getCommentReplyList(videoId: String, commentId: String, page: Int): CommentList = callLavsourceProvider(
        packageName, VideoBusiness::getCommentReplyList, listOf(videoId, commentId, page)
    )

    override fun getEpisodeList(videoId: String): List<VideoEpisodeInfo> = callLavsourceProvider(
        packageName, VideoBusiness::getEpisodeList, listOf(videoId)
    )

    override fun getStreamUrlList(videoId: String, episodeId: String): List<VideoStreamInfo> = callLavsourceProvider(
        packageName, VideoBusiness::getStreamUrlList, listOf(videoId, episodeId)
    )

    override fun getVideoStreamResponse(url: String, range: String?): HttpResponse {
        throw UnsupportedOperationException()
    }

    override fun getDanmakuList(episodeId: String): List<DanmakuInfo> = callLavsourceProvider(
        packageName, VideoBusiness::getDanmakuList, listOf(episodeId)
    )
}