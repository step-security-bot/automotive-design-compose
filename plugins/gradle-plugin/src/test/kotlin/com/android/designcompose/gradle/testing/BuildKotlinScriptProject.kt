package com.android.designcompose.gradle.testing

import org.junit.rules.TemporaryFolder
import java.io.File

class KotlinScriptProject(private val projectDir: TemporaryFolder) {
  fun setup() {
    projectDir
        .newFile("settings.gradle.kts")
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
        """
                .trimIndent())

    projectDir
        .newFile("build.gradle.kts")
        .writeText(
            """
            val agpVersion: String by project
            val kotlinVersion: String by project
            
            plugins {
                kotlin("android") version kotlinVersion
                id("com.android.application") version agpVersion
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
        """.trimIndent())
      this.javaClass::class.java.getResource("/src")?.file?.let { File(it).copyRecursively(projectDir.root) }

  }
}
