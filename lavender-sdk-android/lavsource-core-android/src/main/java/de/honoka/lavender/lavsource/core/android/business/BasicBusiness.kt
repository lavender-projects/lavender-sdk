package de.honoka.lavender.lavsource.core.android.business

import de.honoka.lavender.api.data.LavsourceStatus
import de.honoka.lavender.lavsource.core.android.util.AndroidBusinessStub
import de.honoka.lavender.lavsource.core.android.util.BusinessStubCompanion

interface BasicBusiness {

    fun restartHttpServerIfStopped()

    fun getStatus(): LavsourceStatus
}

class BasicBusinessStub : AndroidBusinessStub(), BasicBusiness {

    companion object : BusinessStubCompanion<BasicBusinessStub>(BasicBusinessStub::class)

    override fun restartHttpServerIfStopped() {
        callProvider(::restartHttpServerIfStopped)
    }

    override fun getStatus(): LavsourceStatus = typedCallProvider(::getStatus)
}
