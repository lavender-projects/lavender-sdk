package de.honoka.lavender.lavsource.core.android.util

import de.honoka.lavender.api.util.LavsourceUtils
import de.honoka.sdk.util.android.basic.AbstractApplicationUtils

abstract class LavenderApplicationUtils : AbstractApplicationUtils() {

    override fun initApplication() {
        LavsourceUtils.initAbstractPart(LavsourceUtilsImpl)
        initAbstractParts()
        initHttpServer()
        initOthers()
    }

    protected open fun initAbstractParts() {}

    protected abstract fun initHttpServer()

    protected open fun initOthers() {}
}
