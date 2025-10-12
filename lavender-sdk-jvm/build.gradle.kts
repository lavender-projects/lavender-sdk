import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.charset.StandardCharsets

plugins {
    java
    `java-library`
    `maven-publish`
    alias(commonLibs.plugins.kotlin)
    alias(commonLibs.plugins.kotlin.kapt)
    alias(commonLibs.plugins.honoka.basic)
}

group = "de.honoka.lavender"
version = commonLibs.versions.p.root.get()

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "de.honoka.gradle.plugin.basic")

    group = rootProject.group

    honoka.basic.dependencies {
        kotlin()
    }

    //noinspection UseTomlInstead
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }

    tasks {
        withType<JavaCompile> {
            options.run {
                encoding = StandardCharsets.UTF_8.name()
                val compilerArgs = compilerArgs as MutableCollection<String>
                compilerArgs += listOf("-parameters")
            }
        }
        
        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
    
    kapt {
        keepJavacAnnotationProcessors = true
    }
}

honoka.basic {
    publishing {
        defineCheckVersionTask()
    }
}
