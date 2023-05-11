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

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    val agpVersion: String = findProperty("agpVersion")?.toString() ?: "8.0.0"
    val kotlinVersion: String = findProperty("kotlinVersion")?.toString() ?: "1.8.21"

    dependencies {
        classpath("com.android.tools.build:gradle:$agpVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {}

subprojects {
    apply {
        configurations.all {
            resolutionStrategy.dependencySubstitution {
                substitute(module("com.android.designcompose.testproj:mylibrary"))
                    .using(project(":mylibrary"))
            }
        }
    }
}
