package de.honoka.lavender.datasource.starter.common

import de.honoka.lavender.datasource.starter.LavenderDataSourceStarterProperties
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletResponse

@ConditionalOnProperty(
    prefix = LavenderDataSourceStarterProperties.PREFIX,
    name = [ "enableDefaultGlobalExceptionHandler" ],
    havingValue = "true",
    matchIfMissing = true
)
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Throwable::class)
    fun handleAll(t: Throwable, response: HttpServletResponse): ApiResponse<*> {
        t.printStackTrace()
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        return ApiResponse.fail(t.message)
    }
}