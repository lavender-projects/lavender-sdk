package de.honoka.lavender.api.business

import cn.hutool.json.JSONObject

interface BasicBusiness {

    fun statusCheck(): JSONObject
}