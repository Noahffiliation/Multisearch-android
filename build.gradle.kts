buildscript {
    repositories {
        google()
        mavenCentral()
    }
    configurations.all {
        resolutionStrategy {
            force(libs.protobuf.java)
            force(libs.protobuf.kotlin)
            force(libs.jdom2)
            force(libs.netty.common)
            force(libs.netty.handler)
            force(libs.netty.codec.http)
            force(libs.netty.codec.http2)
            force(libs.netty.handler.proxy)
            force(libs.netty.codec.socks)
            force(libs.jose4j)
            force(libs.commons.lang3)
            force(libs.httpclient)
            force(libs.bcprov.jdk18on)
            force(libs.bcpkix.jdk18on)
            force(libs.bcutil.jdk18on)
            force(libs.kotlin.gradle.plugin)
            force(libs.guava)
        }
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.sonar) apply false
    alias(libs.plugins.spotless)
}

spotless {
    val commonExcludes =
        listOf(
            "**/build/**",
            "**/.gradle/**",
            ".git/**",
            ".kotlin/**",
            ".android_home/**",
            ".scannerwork/**",
            "artifacts/**",
        )
    kotlin {
        target(
            fileTree(layout.projectDirectory) {
                include("**/*.kt")
                exclude(commonExcludes)
            },
        )
        ktlint()
    }
    kotlinGradle {
        target(
            fileTree(layout.projectDirectory) {
                include("**/*.kts")
                exclude(commonExcludes)
            },
        )
        ktlint()
    }
    format("misc") {
        target(
            fileTree(layout.projectDirectory) {
                include("**/*.md", ".gitignore")
                exclude(commonExcludes)
            },
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("xml") {
        target(
            fileTree(layout.projectDirectory) {
                include("**/*.xml")
                exclude(commonExcludes + ".idea/**")
            },
        )
        trimTrailingWhitespace()
        endWithNewline()
    }
}

dependencyLocking {
    lockAllConfigurations()
}
