import de.honoka.gradle.buildsrc.MavenPublish.checkVersionOfProjects
import de.honoka.gradle.buildsrc.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    //plugins块中不会读取import语句中导入的类
    //引入Versions对象时，必须像这样引入（https://github.com/gradle/gradle/issues/9270）
    @Suppress("RemoveRedundantQualifierName")
    val versions = de.honoka.gradle.buildsrc.Versions
    //plugins
    java
    `maven-publish`
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version versions.kotlin apply false
}

group = "de.honoka.lavender"
version = "1.0.0"

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = rootProject.group

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
        //Test
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }

        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = java.targetCompatibility.toString()
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

tasks.register("checkVersionOfProjects") {
    group = "publishing"
    checkVersionOfProjects()
}