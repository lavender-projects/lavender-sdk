import de.honoka.gradle.buildsrc.MavenPublish.defineCheckVersionOfProjectsTask
import de.honoka.gradle.buildsrc.MavenPublish.setPublishingRepositories

plugins {
    `maven-publish`
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
}

allprojects {
    group = "de.honoka.lavender"
}

version = "1.0.0"

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "io.spring.dependency-management")

    setPublishingRepositories()
}

defineCheckVersionOfProjectsTask()