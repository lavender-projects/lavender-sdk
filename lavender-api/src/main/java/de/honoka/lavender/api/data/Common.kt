package de.honoka.lavender.api.data

import de.honoka.lavender.api.util.MultipleLavsourceIdContainer

data class CommentList(

    var top: Comment? = null,

    var list: List<Comment> = listOf()
) : MultipleLavsourceIdContainer {

    override fun setMultipleLavsourceId(lavsourceId: String) {
        top?.setMultipleLavsourceId(lavsourceId)
        list.forEach {
            it.setMultipleLavsourceId(lavsourceId)
        }
    }
}

data class Comment(

    var id: String? = null,

    var sender: UserInfo? = null,

    var sendDate: String? = null,

    var content: String? = null,

    var likeCount: String? = null,

    var previewReplyList: List<Comment> = listOf(),

    var replyCount: Int? = null,

    var replyCountStr: String? = null
) : MultipleLavsourceIdContainer {

    override fun setMultipleLavsourceId(lavsourceId: String) {
        sender?.lavsourceId = lavsourceId
        previewReplyList.forEach {
            it.setMultipleLavsourceId(lavsourceId)
        }
    }
}