import de.honoka.gradle.plugin.android.ext.kotlinAndroid
import de.honoka.gradle.plugin.android.util.dsl.android
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    `maven-publish`
    alias(libs.plugins.honoka.android)
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "org.jetbrains.kotlin.android")
    apply(plugin = "maven-publish")
    apply(plugin = "de.honoka.gradle.plugin.android")

    val libs = rootProject.libs

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(8)
    }

    android {
        compileSdk = libs.versions.a.compile.sdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.a.min.sdk.get().toInt()
            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    tasks {
        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }
    }

    honoka.basic {
        dependencies {
            kotlinAndroid()
        }
    }
}
