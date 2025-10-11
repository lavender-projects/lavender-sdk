package de.honoka.lavender.lavsource.starter.common

import de.honoka.lavender.lavsource.starter.config.MainConfig
import org.apache.catalina.connector.ClientAbortException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice("${MainConfig.STARTER_BEAN_NAME_PREFIX}GlobalExceptionHandler")
class GlobalExceptionHandler {

    @ExceptionHandler(ClientAbortException::class)
    fun handleClientAbort() {}
}
