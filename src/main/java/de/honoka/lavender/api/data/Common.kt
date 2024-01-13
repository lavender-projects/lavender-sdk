package de.honoka.lavender.api.data

data class CommentList(

    var top: Comment? = null,

    var list: List<Comment> = listOf()
)

data class Comment(

    var id: Long? = null,

    var sender: UserInfo? = null,

    var sendDate: String? = null,

    var content: String? = null,

    var likeCount: String? = null,

    var previewReplyList: List<Comment> = listOf(),

    var replyCount: Int? = null,

    var replyCountStr: String? = null
)