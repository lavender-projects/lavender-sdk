package de.honoka.lavender.lavsource.starter.common

import de.honoka.lavender.lavsource.starter.LavsourceStarterProperties
import de.honoka.sdk.util.framework.web.ApiException
import de.honoka.sdk.util.framework.web.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletResponse

@ConditionalOnProperty(
    prefix = LavsourceStarterProperties.PREFIX,
    name = [ "enableDefaultGlobalExceptionHandler" ],
    havingValue = "true",
    matchIfMissing = true
)
@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(Throwable::class)
    fun handleAll(t: Throwable, response: HttpServletResponse): ApiResponse<*> {
        response.run {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            contentType = MediaType.APPLICATION_JSON_VALUE
            characterEncoding = "UTF-8"
        }
        val responseBody = ApiResponse.fail(t.message)
        if((t is ApiException && t.isPrintStackTrace) || t !is ApiException) {
            log.error("Controller method error", t)
        }
        return responseBody
    }
}