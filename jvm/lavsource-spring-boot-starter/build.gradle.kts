import de.honoka.gradle.buildsrc.MavenPublish.setupVersionAndPublishing
import de.honoka.gradle.buildsrc.Versions

plugins {
    @Suppress("RemoveRedundantQualifierName")
    val versions = de.honoka.gradle.buildsrc.Versions.Jvm
    //plugins
    kotlin("plugin.spring") version versions.kotlin
}

setupVersionAndPublishing("1.0.1-dev")

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${
            Versions.Jvm.LavenderDataSourceStarter.springBootVersion
        }")
    }
}

dependencies {
    implementation("de.honoka.lavender:lavender-api:${
        Versions.Jvm.LavenderDataSourceStarter.lavenderApiVersion
    }".also {
        api(it)
    })
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor".also {
        annotationProcessor(it)
    })
    implementation("de.honoka.sdk:honoka-utils:1.0.8")
    implementation("de.honoka.sdk:honoka-framework-utils:1.0.3")
    implementation("org.hibernate.validator:hibernate-validator")
}

tasks {
    compileJava {
        dependsOn(":jvm:lavender-api:publish")
    }
}