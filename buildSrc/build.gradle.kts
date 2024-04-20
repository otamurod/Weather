plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    // Versions
    val androidVersion = "7.4.2"
    val kotlinVersion = "1.9.10"
    val hiltAndroidPluginVersion = "2.50"
    val kspPluginVersion = "1.9.10-1.0.13"
    val r8Version = "8.2.42"

    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:$androidVersion")

    // Kotlin Gradle Plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

    // Hilt Android Plugin
    implementation("com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidPluginVersion")

    // KSP Plugin
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:$kspPluginVersion")

    // R8
    implementation("com.android.tools:r8:$r8Version")
}

gradlePlugin {
    plugins {
        create("android-module") {
            id = "android-module"
            implementationClass = "buildsrc.plugins.AndroidModulePlugin"
        }
        create("kotlin-module") {
            id = "kotlin-module"
            implementationClass = "buildsrc.plugins.KotlinModulePlugin"
        }
    }
}