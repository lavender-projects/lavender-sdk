package de.honoka.lavender.lavsource.starter

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator

@ComponentScan(
    "de.honoka.lavender.lavsource.starter",
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator::class
)
@EnableConfigurationProperties(MainProperties::class)
@AutoConfiguration
class LavsourceStarter

@ConfigurationProperties(MainProperties.PREFIX)
class MainProperties(

    var remoteAccessHostName: String? = null
) {

    companion object {

        const val PREFIX = "lavender.lavsource"
    }
}
