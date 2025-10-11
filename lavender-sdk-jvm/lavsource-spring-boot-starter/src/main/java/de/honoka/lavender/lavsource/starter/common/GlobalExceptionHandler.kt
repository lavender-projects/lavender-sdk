package de.honoka.lavender.lavsource.starter.common

import org.apache.catalina.connector.ClientAbortException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ClientAbortException::class)
    fun handleClientAbort() {}
}
