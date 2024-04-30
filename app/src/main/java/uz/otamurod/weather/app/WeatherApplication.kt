package uz.otamurod.weather.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.markodevcic.peko.PermissionRequester
import dagger.hilt.android.HiltAndroidApp
import uz.otamurod.domain.preferences.WeatherApplicationPreferencesApi
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application() {
    @Inject
    lateinit var preferences: WeatherApplicationPreferencesApi
    private val localizationDelegate = LocalizationApplicationDelegate()

    override fun onCreate() {
        super.onCreate()
        PermissionRequester.initialize(applicationContext)
        localizationDelegate.onCreate(this)
        toggleTheme()
        preferences.appLanguageCode = "uz"
    }

    private fun toggleTheme() {
        if (preferences.isDarkThemeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, Locale.forLanguageTag("uz"))
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)
        localizationDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(baseContext, super.getResources())
    }
}