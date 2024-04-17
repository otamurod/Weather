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

    // AndroidX
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraintLayout)

    // Retrofit2
    implementation(Dependencies.Retrofit2.retrofit)
    implementation(Dependencies.Retrofit2.converterGson)

    // Dagger - Hilt
    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltAndroidCompiler)
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