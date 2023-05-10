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

import org.spockframework.runtime.model.parallel.ExecutionMode
import spock.lang.Execution
import spock.lang.TempDir
import spock.lang.Unroll

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

@Execution(ExecutionMode.SAME_THREAD)
class StandaloneProjectATDTest extends AbstractTest {
    @TempDir
    File libraryProjectDir
    @TempDir
    File appProjectDir

    def setup() {
        //set up the Library project
        copyResourceIn(libraryProjectDir, "/myapplication")
        copyResourceIn(libraryProjectDir, "/mylibrary")
        copyResourceIn(libraryProjectDir, "/settings.gradle.kts")
        copyResourceIn(libraryProjectDir, "/build.gradle.kts")
        copyResourceIn(libraryProjectDir, "/gradle.properties")

        // Set up the standalone app project
        copyResourceIn(appProjectDir, "/myapplication")
        copyResourceIn(appProjectDir, "/standalone_app")
        copyResourceIn(new File(appProjectDir, "standalone_app"), "/gradle.properties")

        envVars += [
                "ORG_GRADLE_PROJECT_testProjMavenRepo":
                        (new File(libraryProjectDir, "build/testProjRepo")).toString()
        ]

        //Build the repo
        baseGradleRunner()
                .withGradleVersion(gradleVersion)
                .withProjectDir(libraryProjectDir)
                .withArguments("publishReleasePublicationToLocalDirRepository")
                .build()
    }

    @Unroll
    @Slow
    @Execution(ExecutionMode.SAME_THREAD)
    def "Standalone app ATD test with AGP #agpVersion and Gradle #gradleVersion"() {
        given:
        envVars += ["ORG_GRALE_PROJECT_agpVersion": agpVersion]
        when:
        File appRootProjectDir = new File(appProjectDir, "standalone_app")
        def result = baseGradleRunner()
                .withGradleVersion(gradleVersion)
                .withProjectDir(appRootProjectDir)
                .withArguments("atdCheck")
                .build()
        then:
        result.task(":myapplication:atdCheck").outcome == SUCCESS
        where:
        [agpVersion, gradleVersion] << AbstractTest.agpGradleVersionsMap.collect { [it.key, it.value] }
    }
}