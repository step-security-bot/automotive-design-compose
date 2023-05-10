// Copyright 2023 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.android.designcompose.cargoplugin.test

import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import java.util.zip.ZipInputStream

class AbstractTest extends Specification {

    Map<String, String> envVars = System.getenv()

    //Map of AGP version to Gradle version
    static def agpGradleVersionsMap = [
//            "7.3.1"       : "7.5.1",
//            "7.4.2"       : "7.5.1",
//            "8.0.0"       : "8.1.1", // Current versions
            "8.2.0-alpha03": "8.1.1" // Next versions
    ]

    def setup() {}

    def copyResourceIn(File projectDir, String fileName) {
        def resourceFile = new File(getClass().getResource(fileName)?.file)
        if (resourceFile == null) {
            throw Exception("Cannot fine $fileName in resources")
        }
        if (resourceFile.directory) {
            FileUtils.copyDirectory(resourceFile, new File(projectDir, resourceFile.name))
        } else {
            FileUtils.copyFile(resourceFile, new File(projectDir, resourceFile.name))
        }
    }

    def baseGradleRunner() {
        GradleRunner.create().forwardOutput().withPluginClasspath().withEnvironment(envVars)
    }

    def jniLibIsInLibrary(File archive, String target) {
        def expectedName = "$target/librust_in_gradle_test_crate.so"
        println(expectedName)

        def zis = new ZipInputStream((new FileInputStream(archive)))

        def zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            println(zipEntry.name)
            if (zipEntry.name.endsWith(expectedName) && !zipEntry.isDirectory() && zipEntry.compressedSize > 25) {
                return True
            }
            zipEntry = zis.getNextEntry()
        }
        return False
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Slow {}