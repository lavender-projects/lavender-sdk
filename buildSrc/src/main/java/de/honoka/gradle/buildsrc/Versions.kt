package de.honoka.gradle.buildsrc

@Suppress("ConstPropertyName")
object Versions {

    object Jvm {

        object LavenderDataSourceStarter {

            const val springBootVersion  = "2.7.5"

            const val lavenderApiVersion = "1.0.1-dev"
        }

        const val kotlin = "1.6.21"
    }

    object Android {

        object LavsourceAppSdk {

            const val lavenderApi = "1.0.1-dev"

            const val honokaAndroidUtils = "1.1.0-dev"
        }

        const val libraryPlugin = "8.0.0"

        const val kotlin = "1.8.0"
    }

    const val honokaKotlinUtils = "1.0.0-dev"
}