package com.coursework.gradleplugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        applyPlugins(target)
        setProjectConfig(target)
    }

    private fun applyPlugins(project: Project) {
        val libs = project.versionCatalog

        project.pluginManager.apply {
            apply(libs.getPlugin("android-library"))
            apply(libs.getPlugin("jetbrains-kotlin-android"))
        }
    }

    private fun setProjectConfig(project: Project) {
        project.android {
            compileSdk = LibraryProjectConfig.compileSdk

            defaultConfig {
                minSdk = LibraryProjectConfig.minSdk

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    isShrinkResources = true
                }
                debug {
                    isMinifyEnabled = false
                    isShrinkResources = false
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        project.kotlinExtension.jvmToolchain(17)
    }
}