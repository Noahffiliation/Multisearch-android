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
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**", "**/.gradle/**")
        ktlint()
    }
    kotlinGradle {
        target("**/*.kts")
        targetExclude("**/build/**", "**/.gradle/**")
        ktlint()
    }
    format("misc") {
        target("**/*.md", ".gitignore")
        targetExclude("**/build/**", "**/.gradle/**")
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**", "**/.gradle/**", ".idea/**")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

dependencyLocking {
    lockAllConfigurations()
}
