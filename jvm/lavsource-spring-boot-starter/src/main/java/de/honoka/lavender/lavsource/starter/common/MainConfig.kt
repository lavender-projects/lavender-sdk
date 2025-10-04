package de.honoka.lavender.lavsource.starter.common

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(MainProperties::class)
@Configuration("${MainConfig.STARTER_BEAN_NAME_PREFIX}MainConfig")
class MainConfig {

    companion object {

        const val STARTER_BEAN_NAME_PREFIX = "lavsourceStarter"
    }
}

@ConfigurationProperties(MainProperties.PREFIX)
class MainProperties(

    var remoteAccessHostName: String? = null
) {

    companion object {

        const val PREFIX = "lavender.lavsource"
    }
}
