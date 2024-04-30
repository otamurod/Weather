package uz.otamurod.data.preferences

import android.content.Context
import androidx.core.content.edit
import uz.otamurod.domain.preferences.WeatherApplicationPreferencesApi

class WeatherApplicationPreferences(
    context: Context,
    sharedPreferencesName: String
) : WeatherApplicationPreferencesApi {
    private val preferences = context.getSharedPreferences(
        sharedPreferencesName, Context.MODE_PRIVATE
    )

    override var isDarkThemeEnabled: Boolean
        get() = preferences.getBoolean(IS_DARK_THEME_ENABLED, false)
        set(value) {
            preferences.edit { putBoolean(IS_DARK_THEME_ENABLED, value) }
        }

    override var appLanguageCode: String?
        get() = preferences.getString(APP_LANGUAGE_CODE, "en")
        set(value) {
            preferences.edit { putString(APP_LANGUAGE_CODE, value) }
        }

    override var isAutoDetectLocationEnabled: Boolean
        get() = preferences.getBoolean(IS_AUTO_DETECT_LOCATION_ENABLED, true)
        set(value) {
            preferences.edit { putBoolean(IS_AUTO_DETECT_LOCATION_ENABLED, value) }
        }

    companion object {
        private const val IS_DARK_THEME_ENABLED = "IS_DARK_THEME_ENABLED"

        private const val APP_LANGUAGE_CODE = "APP_LANGUAGE_CODE"

        private const val IS_AUTO_DETECT_LOCATION_ENABLED = "IS_AUTO_DETECT_LOCATION_ENABLED"
    }
}