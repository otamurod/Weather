package buildsrc

object Dependencies {
    object AndroidX {
        private const val coreKtxVersion = "1.12.0"
        private const val appCompatVersion = "1.6.1"
        private const val materialVersion = "1.11.0"
        private const val constraintLayoutVersion = "2.1.4"
        private const val vectorDrawableVersion = "1.1.0"
        private const val legacySupportVersion = "1.0.0"
        private const val recyclerViewVersion = "1.3.1"
        private const val fragmentKtxVersion = "1.6.1"
        private const val splashScreenVersion = "1.0.0"

        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val material = "com.google.android.material:material:$materialVersion"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val vectorDrawable = "androidx.vectordrawable:vectordrawable:$vectorDrawableVersion"
        const val legacySupport = "androidx.legacy:legacy-support-v13:$legacySupportVersion"
        const val recyclerView = "androidx.recyclerview:recyclerview:$recyclerViewVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:$fragmentKtxVersion"
        const val splashScreen = "androidx.core:core-splashscreen:$splashScreenVersion"

        object Navigation {
            private const val navigationVersion = "2.7.2"
            private const val safeArgsGradleVersion = "2.7.2"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
            const val safeArgsGradlePlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$safeArgsGradleVersion"
        }

        object Lifecycle {
            private const val extensionsVersion = "2.2.0"
            private const val viewModelVersion = "2.7.0"
            private const val liveDataVersion = "2.4.0"

            const val lifecycleExtensions =
                "androidx.lifecycle:lifecycle-extensions:$extensionsVersion"
            const val lifecycleLiveDataKtx =
                "androidx.lifecycle:lifecycle-livedata-ktx:$liveDataVersion"
            const val lifecycleViewModelKtx =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion"
        }
    }

    object LanguageLocatization {
        private const val localizationVersion = "1.2.11"
        const val localization = "com.akexorcist:localization:$localizationVersion"
    }

    object Lottie {
        private const val lottieVersion = "6.2.0"
        const val lottie = "com.airbnb.android:lottie:$lottieVersion"
    }

    object Multidex {
        private const val multidexVersion = "2.0.1"
        const val multidex = "androidx.multidex:multidex:$multidexVersion"
    }

    object Room {
        private const val roomVersion = "2.6.0"
        const val runtime = "androidx.room:room-runtime:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }

    object Coroutines {
        private const val coroutinesAndroidVersion = "1.7.3"
        private const val coroutinesCoreVersion = "1.7.3"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesAndroidVersion"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion"
    }

    object Retrofit2 {
        private const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    }

    object JodaTime {
        private const val jodaTimeVersion = "2.12.5"
        const val jodaTime = "joda-time:joda-time:$jodaTimeVersion"
    }

    object Chuck {
        private const val chuckLibVersion = "3.5.2"
        private const val chuckLibNoOpVersion = "4.0.0"
        const val library = "com.github.chuckerteam.chucker:library:$chuckLibVersion"
        const val libraryNoOp = "com.github.chuckerteam.chucker:library-no-op:$chuckLibNoOpVersion"
    }

    object Peko {
        const val pekoVersion = "3.0.4"
        const val peko = "com.markodevcic:peko:$pekoVersion"
    }

    object Gson {
        private const val gsonVersiion = "2.9.0"
        const val gson = "com.google.code.gson:gson:$gsonVersiion"
    }

    object Hilt {
        private const val daggerHiltVersion = "2.50"
        private const val androidHiltVersion = "1.1.0"
        const val hiltAndroid = "com.google.dagger:hilt-android:$daggerHiltVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$daggerHiltVersion"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:$androidHiltVersion"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-fragment:$androidHiltVersion"
        const val hiltAndroidGradlePlugin =
            "com.google.dagger:hilt-android-gradle-plugin:$daggerHiltVersion"
    }
}