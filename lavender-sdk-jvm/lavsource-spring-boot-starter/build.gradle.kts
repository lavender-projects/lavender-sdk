import de.honoka.gradle.util.dsl.common
import de.honoka.gradle.util.dsl.libs

plugins {
    alias(commonLibs.plugins.kotlin.spring)
}

honoka.basic {
    publishing.version = libs.common.versions.p.lavsource.spring.boot.starter.get()

    configs {
        java(17)
    }

    dependencies {
        springBootBom()
        springBootConfigProcessor()
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    api(libs.honoka.spring.boot.starter)
    api(libs.common.lavender.api)
    implementation("org.hibernate.validator:hibernate-validator")
}
