@file:Suppress("UnstableApiUsage")

import buildsrc.Config
import buildsrc.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("android-module")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        namespace = Config.packageNameLibPresentation
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

    // AndroidX
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.fragmentKtx)

    // Retrofit2
    implementation(Dependencies.Retrofit2.retrofit)
    implementation(Dependencies.Retrofit2.converterGson)

    // ViewModel, LiveData
    implementation(Dependencies.AndroidX.Lifecycle.lifecycleViewModelKtx)
    implementation(Dependencies.AndroidX.Lifecycle.lifecycleExtensions)
    implementation(Dependencies.AndroidX.Lifecycle.lifecycleLiveDataKtx)

    // Coroutines
    implementation(Dependencies.Coroutines.coroutinesCore)
    implementation(Dependencies.Coroutines.coroutinesAndroid)

    // Navigation
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)

    // Dagger - Hilt
    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltAndroidCompiler)
    ksp(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.hiltNavigation)

    // Peko Dependency
    implementation(Dependencies.Peko.peko)

    // Gson
    implementation(Dependencies.Gson.gson)

    // MultiDex
    implementation(Dependencies.Multidex.multidex)

    // JodaTime
    implementation(Dependencies.JodaTime.jodaTime)

    // Lottie
    implementation(Dependencies.Lottie.lottie)

    // In-app language changing library
    implementation(Dependencies.LanguageLocatization.localization)

    // Map
    implementation(Dependencies.OpenStreetMap.openStreetMap)

    // Location Services
    implementation(Dependencies.GoogleServices.playServicesLocation)

    // Glide Dependencies
    ksp(Dependencies.Glide.glideCompiler)
    implementation(Dependencies.Glide.glide)
}