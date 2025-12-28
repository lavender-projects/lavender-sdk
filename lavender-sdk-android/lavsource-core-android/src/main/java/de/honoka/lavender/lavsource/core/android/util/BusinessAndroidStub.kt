package de.honoka.lavender.lavsource.core.android.util

import de.honoka.lavender.lavsource.core.android.provider.callLavsourceProvider
import kotlin.reflect.KFunction

abstract class BusinessAndroidStub {

    protected lateinit var stubId: String

    protected fun <T : Any> callProvider(businessFunction: KFunction<*>, args: Iterable<Any?>? = null): T = run {
        callLavsourceProvider(stubId, businessFunction, args)
    }
}
