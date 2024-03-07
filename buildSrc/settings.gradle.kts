@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
        google()
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