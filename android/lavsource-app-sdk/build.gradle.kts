import de.honoka.gradle.plugin.android.ext.defaultAar

version = libs.versions.p.lavsource.app.sdk.get()

android {
    namespace = "${project.group}.sdk.android.lavsource"
}

//noinspection UseTomlInstead
dependencies {
    api(libs.lavender.api)
    api(libs.honoka.android.utils)
    implementation("cn.hutool:hutool-all:5.8.18")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

honoka.basic {
    publishing {
        defaultAar()
    }
}
