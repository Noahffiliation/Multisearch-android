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
            force(libs.jose4j)
            force(libs.commons.lang3)
            force(libs.httpclient)
        }
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.sonar) apply false
}
