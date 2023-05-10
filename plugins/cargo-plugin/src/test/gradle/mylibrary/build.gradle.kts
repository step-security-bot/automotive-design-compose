/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UnstableApiUsage")

plugins {
    `maven-publish`
    kotlin("android")
    id("com.android.library")
    id("com.android.designcompose.rust-in-android")
}

android {
    namespace = "com.android.designcompose.testproj.mylibrary"
    compileSdk = 33
    ndkVersion = "25.2.9519653"

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    testOptions.managedDevices {
        devices {
            maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("atd").apply {
                device = "Pixel 5"
                apiLevel = 30
                systemImageSource = "aosp-atd"
            }
        }
    }
    publishing { singleVariant("release") }
}

cargo {
    crateDir.set(layout.projectDirectory.dir("src/main/rust"))
    abi.add("x86")
    abi.add("x86_64")
    abi.add("armeabi-v7a")
    abi.add("arm64-v8a")
}

group = "com.android.designcompose.testproj"

version = "0.1.0"

publishing {
    repositories {
        // Configurable location to output a flat-file repository
        // Set the DesignComposeMavenRepo Gradle Property to a directory to publish the library
        // there.
        // The standalone sample projects will also use the property to find the libraries.
        // See `dev-scripts/test-standalone-projects.sh` for an example.
        val testProjMavenRepo: String? by project
        // This will create the `publish*ToLocalDirRepository` tasks
        maven {
            name = "localDir"
            url = uri(testProjMavenRepo ?: File(rootProject.buildDir, "testProjRepo"))
        }
        publications {
            register<MavenPublication>("release") { afterEvaluate { from(components["release"]) } }
        }
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
