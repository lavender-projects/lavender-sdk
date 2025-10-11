package de.honoka.lavender.lavsource.core.android.business

import cn.hutool.json.JSONObject
import de.honoka.lavender.api.business.BasicBusiness
import de.honoka.lavender.lavsource.core.android.provider.callLavsourceProvider

class BasicBusinessStub(private val packageName: String) : BasicBusiness {

    override fun statusCheck(): JSONObject = run {
        callLavsourceProvider(packageName, BasicBusiness::statusCheck)
    }
}
