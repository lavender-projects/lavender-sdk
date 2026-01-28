import de.honoka.gradle.plugin.android.ext.aarVersion
import de.honoka.gradle.plugin.basic.dsl.api
import de.honoka.gradle.util.dsl.common
import de.honoka.gradle.util.dsl.libs

honoka.basic.publishing.aarVersion = libs.common.versions.p.lavsource.core.android.get()

android {
    namespace = "${project.group}.android.lavsource.sdk"
}

dependencies {
    api(libs.common.lavender.api)
}
