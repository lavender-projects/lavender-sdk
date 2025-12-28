package de.honoka.lavender.api.business

import de.honoka.lavender.api.data.*
import de.honoka.lavender.api.util.BusinessStub
import de.honoka.lavender.api.util.BusinessStubCompanion

interface VideoBusiness {

    fun getRecommendedVideoList(): List<VideoItem>

    fun getVideoDetails(id: String): VideoDetails

    fun getCommentList(videoId: String, sortBy: String, page: Int): CommentList

    fun getCommentReplyList(videoId: String, commentId: String, page: Int): CommentList

    fun getEpisodeList(videoId: String): List<VideoEpisodeInfo>

    fun getStreamUrlList(videoId: String, episodeId: String): List<VideoStreamInfo>

    fun getDanmakuList(episodeId: String): List<DanmakuInfo>
}

class VideoBusinessStub : BusinessStub(), VideoBusiness {

    companion object : BusinessStubCompanion<VideoBusinessStub>(VideoBusinessStub::class)

    override val pathPrefix: String = "/video"

    override fun getRecommendedVideoList(): List<VideoItem> = get("/recommended")

    override fun getVideoDetails(id: String): VideoDetails = run {
        get("/details") {
            form("id", id)
        }
    }

    override fun getCommentList(videoId: String, sortBy: String, page: Int): CommentList = run {
        get("/comment/list") {
            form("videoId", videoId)
            form("sortBy", sortBy)
            form("page", page)
        }
    }

    override fun getCommentReplyList(videoId: String, commentId: String, page: Int): CommentList = run {
        get("/comment/reply/list") {
            form("videoId", videoId)
            form("commentId", commentId)
            form("page", page)
        }
    }

    override fun getEpisodeList(videoId: String): List<VideoEpisodeInfo> = run {
        get("/episode/list") {
            form("videoId", videoId)
        }
    }

    override fun getStreamUrlList(videoId: String, episodeId: String): List<VideoStreamInfo> = run {
        get("/stream/urlList") {
            form("videoId", videoId)
            form("episodeId", episodeId)
        }
    }

    override fun getDanmakuList(episodeId: String): List<DanmakuInfo> = run {
        get("/danmaku/list") {
            form("episodeId", episodeId)
        }
    }
}
