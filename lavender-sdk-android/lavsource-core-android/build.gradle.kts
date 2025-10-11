import de.honoka.gradle.plugin.android.ext.defaultAar
import de.honoka.gradle.plugin.android.ext.kotlinAndroid

plugins {
    alias(libs.plugins.android.library)
    alias(commonLibs.plugins.kotlin.android)
    `maven-publish`
}

version = commonLibs.versions.p.lavsource.core.android.get()

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
}

android {
    namespace = "${project.group}.android.lavsource.sdk"
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

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

honoka.basic {
    dependencies {
        kotlinAndroid()
    }
}

//noinspection UseTomlInstead
dependencies {
    implementation("cn.hutool:hutool-all:5.8.18")
    api(libs.honoka.android.utils)
    api(commonLibs.lavender.api)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

honoka.basic {
    publishing {
        defaultAar(true)
    }
}
