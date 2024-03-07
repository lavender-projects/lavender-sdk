package de.honoka.lavender.lavsource.starter.common

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextHolder : ApplicationContextAware {

    companion object {

        lateinit var context: ApplicationContext

        inline fun <reified T> getBean(): T = context.getBean(T::class.java)
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}