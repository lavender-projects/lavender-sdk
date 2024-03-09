import de.honoka.gradle.buildsrc.MavenPublish.setupVersionAndPublishing
import de.honoka.gradle.buildsrc.Versions

setupVersionAndPublishing("1.0.1-dev")

dependencies {
    listOf(
        "de.honoka.sdk:honoka-kotlin-utils:${Versions.honokaKotlinUtils}"
    ).forEach {
        implementation(it)
        api(it)
    }
}