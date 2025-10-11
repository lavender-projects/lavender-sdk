package de.honoka.lavender.lavsource.starter.service

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

abstract class SystemInitService : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        init()
    }

    abstract fun init()
}
