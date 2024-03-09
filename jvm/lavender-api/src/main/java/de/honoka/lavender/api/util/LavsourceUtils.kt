package de.honoka.lavender.api.util

import de.honoka.sdk.util.kotlin.code.PartialAbstract

interface AbstractLavsourceUtils {

    fun getProxiedImageUrl(url: String): String

    fun getProxiedMediaStreamUrl(url: String): String
}

object LavsourceUtils : PartialAbstract<LavsourceUtils.AbstractPart>(), AbstractLavsourceUtils {

    interface AbstractPart : AbstractLavsourceUtils

    override fun getProxiedImageUrl(url: String): String = abstractPart.getProxiedImageUrl(url)

    override fun getProxiedMediaStreamUrl(url: String): String = abstractPart.getProxiedMediaStreamUrl(url)
}