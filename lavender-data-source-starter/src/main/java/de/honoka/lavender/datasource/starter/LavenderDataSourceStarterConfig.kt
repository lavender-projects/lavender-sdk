package de.honoka.lavender.datasource.starter

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@EnableConfigurationProperties(LavenderDataSourceStarterProperties::class)
@ComponentScan("de.honoka.lavender.datasource.starter")
@AutoConfiguration
class LavenderDataSourceStarterConfig

@ConfigurationProperties(LavenderDataSourceStarterProperties.PREFIX)
data class LavenderDataSourceStarterProperties(

    var enableDefaultGlobalExceptionHandler: Boolean? = null
) {

    companion object {

        const val PREFIX = "lavender.datasource"
    }
}