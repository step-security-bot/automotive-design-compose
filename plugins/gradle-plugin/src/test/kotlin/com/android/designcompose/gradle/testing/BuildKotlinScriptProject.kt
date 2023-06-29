package com.android.designcompose.gradle.testing

import java.io.File
import org.junit.jupiter.api.extension.TestWatcher

class KotlinScriptProject(private val projectDir: File) : TestWatcher {

  fun setup() {
    projectDir
        .resolve("settings.gradle.kts")
        .writeText(
            """
            val DesignComposeMavenRepo: String by settings
            dependencyResolutionManagement {
                repositories {
                    maven(uri(DesignComposeMavenRepo)) {
                        content { includeGroup("com.android.designcompose") }
                    }
                    google() { content { excludeGroupByRegex("com\\.android\\.designcompose.*") } }
                    mavenCentral()
                }
            }
            pluginManagement {
                val kotlinVersion: String by settings
                val agpVersion: String by settings
                plugins {
                     kotlin("android") version kotlinVersion
                     id("com.android.application") version agpVersion
                }
                repositories {
                    gradlePluginPortal()
                    google()
                }
            }
            
        """
                .trimIndent())

    projectDir
        .resolve("build.gradle.kts")
        .writeText(
            """
            plugins {
                kotlin("android") 
                id("com.android.application") 
            }
            android {
                compileSdk = 32
                defaultConfig {
                    minSdkVersion = 30
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
                }
            }
                
            dependencies {
                implementation(libs.designcompose)
                ksp(libs.designcompose.codegen)
                val composeBom = platform(libs.androidx.compose.bom)
                implementation(composeBom)
                androidTestImplementation(composeBom)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.compose.material)
                implementation(libs.material)
            }
        """
                .trimIndent())
    this.javaClass::class.java.getResource("/src")?.file?.let {
      File(it).copyRecursively(projectDir)
    }
  }
}
