package de.honoka.lavender.api.business

import cn.hutool.http.HttpResponse

interface MediaBusiness {

    fun getImageResponse(url: String): HttpResponse

    fun getVideoResponse(url: String, range: String?): HttpResponse
}
