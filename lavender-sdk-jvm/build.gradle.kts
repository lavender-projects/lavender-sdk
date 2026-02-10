import de.honoka.gradle.util.data.classifyProjects
import de.honoka.gradle.util.dsl.applier
import de.honoka.gradle.util.dsl.common
import de.honoka.gradle.util.dsl.libs
import de.honoka.gradle.util.dsl.projects

plugins {
    alias(commonLibs.plugins.kotlin) apply false
    alias(commonLibs.plugins.kotlin.kapt) apply false
    alias(commonLibs.plugins.kotlin.spring) apply false
    alias(commonLibs.plugins.honoka.basic)
}

group = "de.honoka.lavender"
version = libs.common.versions.p.root.get()

val projects = classifyProjects {
    springBoot = projects("lavsource-spring-boot-starter")
}

subprojects {
    applier {
        java
        `java-library`
        `maven-publish`
        kotlin
        `kotlin-kapt`
        if(project in projects.springBoot) {
            `kotlin-spring`
        }
        `honoka-basic`
    }

    group = rootProject.group

    honoka.basic {
        configs {
            java(if(project in projects.springBoot) 17 else 8, true)
            kotlin()
            kapt()
        }

        dependencies {
            kotlin()
            if(project in projects.springBoot) {
                springBootBom()
                springBootConfigProcessor()
            }
        }
    }
}

honoka.basic {
    publishing {
        defineCheckVersionTask()
    }
}
