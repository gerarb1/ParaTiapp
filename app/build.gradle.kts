
    plugins {
        alias(libs.plugins.androidApplication) // Asegúrate de que los nombres coincidan con tu TOML
        alias(libs.plugins.kotlinAndroid)
        alias(libs.plugins.kotlinCompose)
        alias(libs.plugins.ksp)
        alias(libs.plugins.room)
        alias(libs.plugins.kotlinSerialization) // Necesitaremos añadir este al TOML
    }



android {
    namespace = "com.para_ti.chocoapp"
    compileSdk = 36 // O 34, según lo que tengas instalado

    defaultConfig {
        applicationId = "com.para_ti.chocoapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // CORREGIDO: La asignación se hace con '='
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // CORREGIDO: Bloque movido a su lugar correcto
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // CORREGIDO: Este bloque debe estar aquí
    buildFeatures {
        compose = true
    }

    buildTypes { // Es buena práctica tenerlo definido
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // ELIMINADO: Este bloque causaba el problema principal
    // composeOptions {
    //     kotlinCompilerExtensionVersion = "1.5.4"
    // }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
room {
    schemaDirectory("$projectDir/schemas")
}
dependencies {
    // --- Room (Base de datos) ---
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:${room_version}")
    implementation("androidx.room:room-ktx:${room_version}")
    ksp("androidx.room:room-compiler:$room_version")
    // --- Jetpack Compose ---
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

// --- Compose UI y Material ---
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

// --- Material Design 3 ---
    implementation("androidx.compose.material3:material3")

// --- Iconos de Material (core + extended) ---
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

// --- Integraciones con Android ---
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.navigation:navigation-compose:2.7.7")

// --- Runtime y LiveData ---
    implementation("androidx.compose.runtime:runtime-livedata")
}
