package de.honoka.lavender.lavsource.core.android.business

import de.honoka.lavender.api.data.LavsourceStatus
import de.honoka.lavender.api.util.BusinessStubCompanion
import de.honoka.lavender.lavsource.core.android.util.BusinessAndroidStub

interface BasicBusiness {

    fun getStatus(): LavsourceStatus
}

class BasicBusinessStub : BusinessAndroidStub(), BasicBusiness {

    companion object : BusinessStubCompanion<BasicBusinessStub>(BasicBusinessStub::class)

    override fun getStatus(): LavsourceStatus = callProvider(::getStatus)
}
