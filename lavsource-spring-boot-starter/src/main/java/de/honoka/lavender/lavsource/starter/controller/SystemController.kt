package de.honoka.lavender.lavsource.starter.controller

import de.honoka.lavender.lavsource.starter.LavsourceStarterProperties
import de.honoka.sdk.util.framework.web.ApiException
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/system")
@RestController
class SystemController(
    private val starterProperties: LavsourceStarterProperties
) {

    @GetMapping("/serverName")
    fun serverName(): ApiResponse<String> = ApiResponse.success(starterProperties.serverName)

    @GetMapping("/ping")
    fun ping(@RequestParam(required = false) serverName: String?): ApiResponse<Unit> {
        if(serverName != starterProperties.serverName) {
            throw ApiException("传入的服务器名称与实际名称（${starterProperties.serverName}）不匹配")
        }
        return ApiResponse.success(null)
    }
}