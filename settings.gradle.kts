@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
        google()
        mavenLocal()
        maven("https://mirrors.honoka.de/maven-repo")
    }
}

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

rootProject.name = "lavender-sdk"

include("jvm")
include("jvm:lavender-api")
include("jvm:lavsource-spring-boot-starter")
include("android")
include("android:lavsource-app-sdk")
