package de.honoka.lavender.api.data

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

    var senderIdHash: String? = null
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
)

data class VideoEpisodeInfo(

    var id: Long? = null,

    var name: String? = null
)

data class VideoStreamInfo(

    var type: String? = null,

    var qualityId: Int? = null,

    var qualityName: String? = null,

    var videoStreamUrl: String? = null,

    var audioStreamUrl: String? = null
)