import designcompose.conventions.publish.basePom

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    id("designcompose.conventions.base")
    id("designcompose.conventions.publish.common")
}

gradlePlugin {
    plugins {
        create("designcompose-gradle-plugin") {
            id = "com.android.designcompose"
            displayName = "com.android.designcompose.gradle.plugin"
            implementationClass = "com.android.designcompose.gradle.Plugin"
        }
    }
}

publishing {
    publications.create<MavenPublication>("pluginMaven") {
        pom {
            basePom()
            artifactId = "gradle-plugin"
            name.set("Automotive Design for Compose Plugin")
            description.set( "Plugin that adds base configuration and assisting tasks to DesignCompose-enabled apps")
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}
