package com.android.designcompose.gradle.testing

import java.io.File

class SdkRepo(val testRootDir: File, val sdkRootDir: File) {
    val absolutePath = File(testRootDir, "designComposeM2Repo").absolutePath

    fun build() {

    }
}