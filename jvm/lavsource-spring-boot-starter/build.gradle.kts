plugins {
    alias(libs.plugins.kotlin.spring)
}

version = libs.versions.p.lavsource.spring.boot.starter.get()

honoka.basic.dependencies {
    springBootBom()
}

//noinspection UseTomlInstead
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    val configProcessor = "org.springframework.boot:spring-boot-configuration-processor:${
        libs.versions.d.spring.boot.get()
    }"
    kapt(configProcessor)
    api(libs.lavender.api)
    implementation(libs.honoka.spring.boot.starter)
    implementation("org.hibernate.validator:hibernate-validator")
}

honoka.basic.publishing {
    default()
}
