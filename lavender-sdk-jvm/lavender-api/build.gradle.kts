import de.honoka.gradle.util.dsl.common
import de.honoka.gradle.util.dsl.libs

honoka.basic.publishing.version = libs.common.versions.p.lavender.api.get()

dependencies {
    compileOnly(libs.common.honoka.kotlin.utils)
}
