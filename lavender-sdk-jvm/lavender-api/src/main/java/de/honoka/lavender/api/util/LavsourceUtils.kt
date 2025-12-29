package de.honoka.lavender.api.util

import de.honoka.lavender.api.util.LavsourceUtils.AbstractPart
import de.honoka.sdk.util.kotlin.various.DirectProxy
import de.honoka.sdk.util.kotlin.various.PartialAbstract

class LavsourceUtils private constructor() {

    interface AbstractPart {

        fun getProxiedImageUrl(url: String): String

        fun getProxiedMediaStreamUrl(url: String): String
    }

    companion object : AbstractPart by abstractPartDelegate, PartialAbstract<AbstractPart> {

        override lateinit var abstractPart: AbstractPart
    }
}

private val abstractPartDelegate = DirectProxy.of(
    { LavsourceUtils.abstractPart }, AbstractPart::class
)
