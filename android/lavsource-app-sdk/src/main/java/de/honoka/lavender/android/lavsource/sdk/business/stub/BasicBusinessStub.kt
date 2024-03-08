package de.honoka.lavender.android.lavsource.sdk.business.stub

import cn.hutool.json.JSONObject
import de.honoka.lavender.android.lavsource.sdk.util.callLavsourceProvider
import de.honoka.lavender.api.business.BasicBusiness

class BasicBusinessStub(private val packageName: String) : BasicBusiness {

    override fun statusCheck(): JSONObject = callLavsourceProvider(
        packageName, BasicBusiness::statusCheck
    )
}