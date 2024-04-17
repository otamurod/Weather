package uz.otamurod.weather.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.multidex.MultiDex
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.markodevcic.peko.PermissionRequester
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class WeatherApplication : Application() {

    private val localizationDelegate = LocalizationApplicationDelegate()

    override fun onCreate() {
        super.onCreate()
        PermissionRequester.initialize(applicationContext)
        localizationDelegate.onCreate(this)
    }

    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, Locale.ENGLISH)
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