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
        namespace = buildsrc.Config.packageNameLibData
    }
}

dependencies {
    // Inject Specifications Layer
    implementation(project(":specs"))

    // Inject Domain Layer
    implementation(project(":domain"))

    // Kotlin Coroutines
    implementation(Dependencies.Coroutines.coroutinesCore)
    implementation(Dependencies.Coroutines.coroutinesAndroid)

    // Room, Kotlin Extensions and Coroutines support for Room
    implementation(Dependencies.Room.runtime)
    ksp(Dependencies.Room.compiler)
    implementation(Dependencies.Room.roomKtx)

    // Retrofit
    implementation(Dependencies.Retrofit2.retrofit)
    implementation(Dependencies.Retrofit2.converterGson)

    // Dagger - Hilt
    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltAndroidCompiler)
    ksp(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.hiltNavigation)

    // LiveData
    implementation(Dependencies.AndroidX.Lifecycle.lifecycleLiveDataKtx)

    // Chuck interceptor
    debugImplementation(Dependencies.Chuck.library)
    releaseImplementation(Dependencies.Chuck.libraryNoOp)
}