// C:/Users/Usuario/Desktop/Appk/build.gradle.kts

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinCompose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false

    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false

}
