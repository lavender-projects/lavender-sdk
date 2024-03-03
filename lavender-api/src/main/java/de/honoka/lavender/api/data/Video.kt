package de.honoka.lavender.api.data

import de.honoka.lavender.api.util.MultipleLavsourceIdContainer

data class RecommendedVideoItem(

    var videoId: String? = null,

    var lavsourceId: String? = null,

    var title: String? = null,

    var author: String? = null,

    var coverImg: String? = null,

    var playCount: String? = null,

    var danmakuCount: String? = null,

    var duration: String? = null
)

data class DanmakuInfo(

    var content: String? = null,

    var time: Double? = null,

    var type: String? = null,

    var fontSize: Int? = null,

    var colorRgb: String? = null,

    var senderId: String? = null
)

data class VideoDetails(

    var id: String? = null,

    var lavsourceId: String? = null,

    var uploader: UserInfo = UserInfo(),

    var title: String? = null,

    var description: String? = null,

    var tagList: List<String> = listOf(),

    var playCount: String? = null,

    var danmakuCount: String? = null,

    var publishTime: String? = null,

    var replyCount: String? = null,

    var likeCount: String? = null,

    //bilibili独有，其他平台可用其他数据代替填入
    var coinCount: String? = null,

    var collectCount: String? = null,

    var shareCount: String? = null,

    var relatedVideoList: List<RecommendedVideoItem> = listOf()
) : MultipleLavsourceIdContainer {

    override fun setMultipleLavsourceId(lavsourceId: String) {
        this.lavsourceId = lavsourceId
        uploader.lavsourceId = lavsourceId
        relatedVideoList.forEach {
            it.lavsourceId = lavsourceId
        }
    }
}

data class VideoEpisodeInfo(

    var id: String? = null,

    var name: String? = null
)

data class VideoStreamInfo(

    var type: String? = null,

    var qualityId: String? = null,

    var qualityName: String? = null,

    var videoStreamUrl: String? = null,

    var audioStreamUrl: String? = null
)