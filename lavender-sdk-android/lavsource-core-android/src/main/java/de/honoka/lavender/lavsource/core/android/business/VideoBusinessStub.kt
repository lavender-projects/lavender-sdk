package de.honoka.lavender.lavsource.core.android.business

import de.honoka.lavender.api.business.VideoBusiness
import de.honoka.lavender.api.data.*
import de.honoka.lavender.lavsource.core.android.util.BusinessStubCompanion
import de.honoka.lavender.lavsource.core.android.util.WebBusinessStub

class VideoBusinessStub : WebBusinessStub(), VideoBusiness {

    companion object : BusinessStubCompanion<VideoBusinessStub>(VideoBusinessStub::class)

    override val pathPrefix: String = "/video"

    override fun getRecommendedVideoList(): List<VideoItem> = get("/recommended")

    override fun getVideoDetails(id: String): VideoDetails {
        return get("/details") {
            form("id", id)
        }
    }

    override fun getCommentList(videoId: String, sortBy: String, page: Int): CommentList {
        return get("/comment/list") {
            form("videoId", videoId)
            form("sortBy", sortBy)
            form("page", page)
        }
    }

    override fun getCommentReplyList(videoId: String, commentId: String, page: Int): CommentList {
        return get("/comment/reply/list") {
            form("videoId", videoId)
            form("commentId", commentId)
            form("page", page)
        }
    }

    override fun getEpisodeList(videoId: String): List<VideoEpisodeInfo> {
        return get("/episode/list") {
            form("videoId", videoId)
        }
    }

    override fun getStreamUrlList(videoId: String, episodeId: String): List<VideoStreamInfo> {
        return get("/stream/urlList") {
            form("videoId", videoId)
            form("episodeId", episodeId)
        }
    }

    override fun getDanmakuList(episodeId: String): List<DanmakuInfo> {
        return get("/danmaku/list") {
            form("episodeId", episodeId)
        }
    }
}
