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


import spock.lang.Unroll

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class MultiProjectLibraryCompileTest extends AbstractMultiProjectTest {

    @Unroll
    def "Multi-project library publish with AGP #agpVersion and Gradle #gradleVersion"() {
        given:
        def mavenRepo = new File(testProjectDir, "build/testProjRepo")
        File publishedAAR = new File(mavenRepo, "com/android/designcompose/testproj/mylibrary/0.1.0/mylibrary-0.1.0.aar")

        envVars += ["ORG_GRADLE_PROJECT_testProjMavenRepo": (mavenRepo).toString()]
        envVars += ["ORG_GRADLE_PROJECT_agpVersion": agpVersion]

        when:
        def result = baseGradleRunner()
                .withGradleVersion(gradleVersion)
                .withProjectDir(testProjectDir)
                .withArguments("publishReleasePublicationToLocalDirRepository")
                .build()

        then:
        result.task(":mylibrary:publishReleasePublicationToLocalDirRepository").outcome == SUCCESS
        publishedAAR.exists()
        println(publishedAAR.absolutePath)
        jniLibIsInLibrary(publishedAAR, "x86")
        jniLibIsInLibrary(publishedAAR, "x86_64")
        jniLibIsInLibrary(publishedAAR, "armeabi")
        jniLibIsInLibrary(publishedAAR, "arm64-v8a")

        where:
        [agpVersion, gradleVersion] << AbstractTest.agpGradleVersionsMap.collect { [it.key, it.value] }
    }
}