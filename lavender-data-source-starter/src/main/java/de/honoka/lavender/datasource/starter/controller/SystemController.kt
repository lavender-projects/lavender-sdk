package de.honoka.lavender.datasource.starter.controller

import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/system")
@RestController
class SystemController {

    @GetMapping("/ping")
    fun ping(): ApiResponse<*> = ApiResponse.success(null)
}