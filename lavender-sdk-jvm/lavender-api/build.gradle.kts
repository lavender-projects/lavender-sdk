version = commonLibs.versions.p.lavender.api.get()

java {
    toolchain.languageVersion = JavaLanguageVersion.of(8)
    withSourcesJar()
}

dependencies {
    compileOnly(commonLibs.honoka.kotlin.utils)
}

honoka.basic.publishing {
    default()
}
