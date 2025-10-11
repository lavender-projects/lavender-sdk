package de.honoka.lavender.lavsource.core.android.business

import cn.hutool.http.HttpResponse
import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.lavender.lavsource.core.android.provider.callLavsourceProvider

class VideoBusinessStub(private val packageName: String) : VideoBusiness {

    override fun getRecommendedVideoList(): List<RecommendedVideoItem> = run {
        callLavsourceProvider(packageName, VideoBusiness::getRecommendedVideoList)
    }

    override fun getVideoDetails(id: String): VideoDetails = run {
        callLavsourceProvider(
            packageName, VideoBusiness::getVideoDetails, listOf(id)
        )
    }

    override fun getCommentList(videoId: String, sortBy: String, page: Int): CommentList = run {
        callLavsourceProvider(
            packageName,
            VideoBusiness::getCommentList,
            listOf(videoId, sortBy, page)
        )
    }

    override fun getCommentReplyList(videoId: String, commentId: String, page: Int): CommentList = run {
        callLavsourceProvider(
            packageName,
            VideoBusiness::getCommentReplyList,
            listOf(videoId, commentId, page)
        )
    }

    override fun getEpisodeList(videoId: String): List<VideoEpisodeInfo> = run {
        callLavsourceProvider(
            packageName, VideoBusiness::getEpisodeList, listOf(videoId)
        )
    }

    override fun getStreamUrlList(videoId: String, episodeId: String): List<VideoStreamInfo> = run {
        callLavsourceProvider(
            packageName,
            VideoBusiness::getStreamUrlList,
            listOf(videoId, episodeId)
        )
    }

    override fun getVideoStreamResponse(url: String, range: String?): HttpResponse {
        throw UnsupportedOperationException()
    }

    override fun getDanmakuList(episodeId: String): List<DanmakuInfo> = run {
        callLavsourceProvider(
            packageName, VideoBusiness::getDanmakuList, listOf(episodeId)
        )
    }
}
