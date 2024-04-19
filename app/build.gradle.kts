@file:Suppress("UnstableApiUsage")

import buildsrc.Config
import buildsrc.Dependencies

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("android-module")
}

android {
    defaultConfig {
        applicationId = Config.applicationId
        namespace = Config.packageNameApp

        /**
         * Data-level
         */
        buildConfigField("String", "OPEN_METEO_API_BASE_URL", Config.API.OPEN_METEO_API_BASE_URL)
        buildConfigField("String", "GEO_CODING_API_BASE_URL", Config.API.GEO_CODING_API_BASE_URL)
        buildConfigField("String", "SHARED_PREF_NAME", Config.SharedPreferences.SHARED_PREF_NAME)

        /**
         * Presentation-level
         */
        buildConfigField("String", "BUILD_VERSION", "\"${Config.Build.versionName}\"")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Inject Specifications Layer
    implementation(project(":specs"))

    // Inject Domain Layer
    implementation(project(":domain"))

    // Inject Data Layer
    implementation(project(":data"))

    // Inject Presentation Layer
    implementation(project(":presentation"))

    // AndroidX
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraintLayout)

    // Room, Kotlin Extensions and Coroutines support for Room
    implementation(Dependencies.Room.runtime)
    ksp(Dependencies.Room.compiler)
    implementation(Dependencies.Room.roomKtx)

    // Retrofit2
    implementation(Dependencies.Retrofit2.retrofit)
    implementation(Dependencies.Retrofit2.converterGson)

    // Dagger - Hilt
    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltAndroidCompiler)
    ksp(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.hiltNavigation)

    // Chuck interceptor
    debugImplementation(Dependencies.Chuck.library)
    releaseImplementation(Dependencies.Chuck.libraryNoOp)

    // In-app language changing library
    implementation(Dependencies.LanguageLocatization.localization)

    // MultiDex
    implementation(Dependencies.Multidex.multidex)

    // Peko Dependency
    implementation(Dependencies.Peko.peko)
}