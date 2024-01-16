package de.honoka.lavender.lavsource.starter

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@EnableConfigurationProperties(LavsourceStarterProperties::class)
@ComponentScan("de.honoka.lavender.lavsource.starter")
@AutoConfiguration
class LavsourceStarterConfig

@ConfigurationProperties(LavsourceStarterProperties.PREFIX)
data class LavsourceStarterProperties(

    var enableDefaultGlobalExceptionHandler: Boolean? = null,

    var serverName: String? = null
) {

    companion object {

        const val PREFIX = "lavender.lavsource"
    }
}