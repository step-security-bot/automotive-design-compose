package com.android.designcompose.gradle.testing

import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder

object Tests {
    @Rule
    @JvmField
    val tmpDir = TemporaryFolder()


    lateinit var project: KotlinScriptProject

    @Before
    fun setup() {
        project = KotlinScriptProject(tmpDir)
        project.setup()
    }

    @Test
    fun basicTest() {
        val args = listOf("-PagpVersion=8.0.1", "-PkotlinVersion=1.8.0", "assembleDebug")
        GradleRunner.create().withPluginClasspath().withProjectDir(tmpDir.root).withArguments(args).build()
    }

}