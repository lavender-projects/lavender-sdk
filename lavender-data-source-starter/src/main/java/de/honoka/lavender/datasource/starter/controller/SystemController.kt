package de.honoka.lavender.datasource.starter.controller

import de.honoka.lavender.datasource.starter.LavenderDataSourceStarterProperties
import de.honoka.sdk.util.framework.web.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/system")
@RestController
class SystemController(
    private val starterProperties: LavenderDataSourceStarterProperties
) {

    @GetMapping("/serverName")
    fun serverName(): ApiResponse<String> = ApiResponse.success(starterProperties.serverName)

    @GetMapping("/ping")
    fun ping(@RequestParam(required = false) serverName: String?): ApiResponse<Unit> {
        if(serverName != starterProperties.serverName) {
            throw Exception("传入的服务器名称与实际名称（${starterProperties.serverName}）不匹配")
        }
        return ApiResponse.success(null)
    }
}