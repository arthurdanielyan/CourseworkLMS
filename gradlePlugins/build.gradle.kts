import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("androidPlugin") {
            id = "com.coursework.android.library"
            implementationClass = "com.coursework.gradlePlugins.AndroidLibraryPlugin"
            version = "1.0.0"
        }
        create("usesComposePlugin") {
            id = "com.coursework.uses.compose"
            implementationClass = "com.coursework.gradlePlugins.UsesComposePlugin"
            version = "1.0.0"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.tools.build.gradle)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    jvmTarget.set(JvmTarget.JVM_17)
}
