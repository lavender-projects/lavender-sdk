package de.honoka.lavender.android.lavsource.sdk.provider

import cn.hutool.json.JSONArray

data class LavsourceProviderRequest(

    var className: String? = null,

    var method: String? = null,

    var args: JSONArray = JSONArray()
)