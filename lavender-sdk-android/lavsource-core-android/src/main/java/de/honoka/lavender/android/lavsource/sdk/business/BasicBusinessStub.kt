package de.honoka.lavender.android.lavsource.sdk.business

import cn.hutool.json.JSONObject
import de.honoka.lavender.android.lavsource.sdk.provider.callLavsourceProvider
import de.honoka.lavender.api.business.BasicBusiness

class BasicBusinessStub(private val packageName: String) : BasicBusiness {

    override fun statusCheck(): JSONObject = run {
        callLavsourceProvider(packageName, BasicBusiness::statusCheck)
    }
}
