package de.honoka.lavender.lavsource.starter.service

import de.honoka.lavender.api.util.LavsourceUtils
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import javax.annotation.Resource

abstract class SystemInitService : ApplicationRunner {

    @Resource
    private lateinit var lavsourceUtilsAbstractPart: LavsourceUtils.AbstractPart

    override fun run(args: ApplicationArguments) {
        defaultInit()
        init()
    }

    private fun defaultInit() {
        LavsourceUtils.initAbstractPart(lavsourceUtilsAbstractPart)
    }

    abstract fun init()
}