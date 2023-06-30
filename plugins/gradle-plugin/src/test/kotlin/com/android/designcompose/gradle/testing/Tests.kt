package com.android.designcompose.gradle.testing

import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir

class Tests {

  companion object {
    lateinit var project: KotlinScriptProject
    @BeforeAll
    @JvmStatic
    fun setup(@TempDir(cleanup = CleanupMode.NEVER) testProjectDir: File) {
      project = KotlinScriptProject(testProjectDir)
      project.setup()
    }
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
        .withProjectDir(project.projectDir)
        .withArguments(args)
        .build()
  }
}
