plugins {
    alias(libs.plugins.honoka.basic)
}

version = libs.versions.p.root.get()

allprojects {
    group = "de.honoka.lavender"
}

honoka.basic {
    publishing {
        defineCheckVersionTask()
    }
}

libs.versions.d.kotlin.coroutines
libs.versions.d.spring.boot
