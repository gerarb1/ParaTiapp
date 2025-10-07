// C:/Users/Usuario/Desktop/Appk/build.gradle.kts

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false // Nota: asegúrate que el nombre coincida con tu TOML
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinCompose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false // <-- ¡CORREGIDO! Ahora usa el alias
}
