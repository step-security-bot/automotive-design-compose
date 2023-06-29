package com.android.designcompose.gradle.testing

import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir

class Tests {

  @TempDir(cleanup = CleanupMode.NEVER) var testProjectDir: File? = null

  lateinit var project: KotlinScriptProject

  @BeforeEach
  fun setup() {
    project = KotlinScriptProject(testProjectDir!!)
    project.setup()
  }

  @Test
  fun basicTest() {
    val args =
        listOf(
            "-PagpVersion=8.0.1",
            "-PkotlinVersion=1.8.0",
            "assembleDebug",
            "-PDesignComposeMavenRepo=/usr/local/google/home/froeht/git/designcompose-github/build/designcompose_m2repo")
    GradleRunner.create()
        .withPluginClasspath()
        .withProjectDir(testProjectDir)
        .withArguments(args)
        .build()
  }
}
