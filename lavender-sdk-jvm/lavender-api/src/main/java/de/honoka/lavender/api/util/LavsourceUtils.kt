package de.honoka.lavender.api.util

import de.honoka.lavender.api.util.LavsourceUtils.AbstractPart
import de.honoka.sdk.util.kotlin.lang.DirectProxy

class LavsourceUtils private constructor() {

    interface AbstractPart {

        fun getProxiedImageUrl(url: String): String

        fun getProxiedMediaStreamUrl(url: String): String
    }

    companion object : AbstractPart by abstractPartDelegate {

        lateinit var abstractPart: AbstractPart
    }
}

private val abstractPartDelegate = DirectProxy.of(
    { LavsourceUtils.abstractPart }, AbstractPart::class
)
