import de.honoka.gradle.util.dsl.applier
import de.honoka.gradle.util.dsl.common
import de.honoka.gradle.util.dsl.libs

plugins {
    alias(commonLibs.plugins.kotlin) apply false
    alias(commonLibs.plugins.kotlin.kapt) apply false
    alias(commonLibs.plugins.honoka.basic)
}

group = "de.honoka.lavender"
version = libs.common.versions.p.root.get()

subprojects {
    applier {
        java
        `java-library`
        `maven-publish`
        alias(libs.common.plugins.kotlin)
        alias(libs.common.plugins.kotlin.kapt)
        alias(libs.common.plugins.honoka.basic)
    }

    group = rootProject.group

    honoka.basic {
        configs {
            java(8, true)
            kotlin()
            kapt()
        }

        dependencies {
            kotlin()
        }
    }
}

honoka.basic {
    publishing {
        defineCheckVersionTask()
    }
}
