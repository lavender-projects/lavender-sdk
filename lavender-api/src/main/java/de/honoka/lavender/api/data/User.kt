package de.honoka.lavender.api.data

data class UserInfo(

    var id: Long? = null,

    var lavsourceId: String? = null,

    var name: String? = null,

    var avatar: String? = null,

    var level: Int? = null,

    var followerCount: String? = null,

    var publishedVideosCount: String? = null,

    var ipLocation: String? = null
)