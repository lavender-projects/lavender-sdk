package de.honoka.lavender.android.lavsource.sdk.util

import android.app.Application
import de.honoka.sdk.util.android.common.GlobalComponents
import de.honoka.sdk.util.kotlin.code.PartialAbstract

object ApplicationUtils : PartialAbstract<ApplicationUtils.AbstractPart>() {

    interface AbstractPart {

        fun initPartialAbstractObjects()

        fun initHttpServer()
    }

    fun initApplication(application: Application) = abstractPart.run {
        GlobalComponents.application = application
        initHttpServer()
        initPartialAbstractObjects()
    }
}