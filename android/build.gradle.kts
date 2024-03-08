plugins {
    val versions = de.honoka.gradle.buildsrc.Versions.Android
    //plugins
    id("com.android.library") version versions.libraryPlugin
    kotlin("android") version versions.kotlin
}

android {
    namespace = "${project.group}.sdk.android"
    compileSdk = 33
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "org.jetbrains.kotlin.android")

    android {
        compileSdk = 33

        defaultConfig {
            minSdk = 26
            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                @Suppress("UnstableApiUsage")
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = sourceCompatibility
        }

        kotlinOptions {
            jvmTarget = compileOptions.sourceCompatibility.toString()
        }
    }
}