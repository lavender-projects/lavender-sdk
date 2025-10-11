package de.honoka.lavender.api.util

import de.honoka.sdk.util.kotlin.various.PartialAbstract

class LavsourceUtils private constructor() {

    interface AbstractPart {

        fun getProxiedImageUrl(url: String): String

        fun getProxiedMediaStreamUrl(url: String): String
    }

    companion object : AbstractPart by LavsourceUtils.abstractPart, PartialAbstract<AbstractPart> {

        override lateinit var abstractPart: AbstractPart
    }
}
