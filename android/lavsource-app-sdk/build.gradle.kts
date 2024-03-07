import de.honoka.gradle.buildsrc.MavenPublish.setupAndroidAarVersionAndPublishing
import de.honoka.gradle.buildsrc.Versions

setupAndroidAarVersionAndPublishing("1.0.0-dev")

android {
    namespace = "${project.group}.android.lavsource.sdk"
}

dependencies {
    implementation("de.honoka.lavender:lavender-api:${Versions.Android.LavsourceAppSdk.lavenderApi}".also {
        api(it)
    })
    implementation("de.honoka.sdk:honoka-kotlin-utils:1.0.0-dev")
    implementation("de.honoka.sdk:honoka-framework-utils:1.0.4")
    implementation("cn.hutool:hutool-all:5.8.18")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

tasks {
    preBuild {
        dependsOn(":jvm:lavender-api:publish")
    }
}