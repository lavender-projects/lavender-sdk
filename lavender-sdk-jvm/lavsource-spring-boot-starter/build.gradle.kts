plugins {
    alias(commonLibs.plugins.kotlin.spring)
}

version = commonLibs.versions.p.lavsource.spring.boot.starter.get()

honoka.basic {
    dependencies {
        springBootBom()
        springBootConfigProcessor()
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(libs.honoka.spring.boot.starter)
    api(commonLibs.lavender.api)
    implementation("org.hibernate.validator:hibernate-validator")
}

honoka.basic {
    publishing {
        default()
    }
}
