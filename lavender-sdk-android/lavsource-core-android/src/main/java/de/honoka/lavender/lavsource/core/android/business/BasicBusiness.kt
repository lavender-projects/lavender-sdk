package de.honoka.lavender.lavsource.core.android.business

import cn.hutool.json.JSONObject
import de.honoka.lavender.lavsource.core.android.provider.callLavsourceProvider

interface BasicBusiness {

    fun statusCheck(): JSONObject
}

class BasicBusinessStub(private val packageName: String) : BasicBusiness {

    override fun statusCheck(): JSONObject = run {
        callLavsourceProvider(packageName, BasicBusiness::statusCheck)
    }
}
