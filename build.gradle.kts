import de.honoka.gradle.buildsrc.MavenPublish.checkVersionOfProjects
import de.honoka.gradle.buildsrc.MavenPublish.setupVersionAndPublishing
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
}

group = "de.honoka.lavender"
setupVersionAndPublishing("1.0.0")

allprojects {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
    }

    dependencies {
        //Test
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }

        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        test {
            useJUnitPlatform()
        }
    }

    publishing {
        repositories {
            mavenLocal()
            if(hasProperty("remoteMavenRepositoryUrl")) {
                maven(properties["remoteMavenRepositoryUrl"]!!)
            }
        }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = rootProject.group
}

tasks.register("checkVersionOfProjects") {
    group = "publishing"
    checkVersionOfProjects()
}