package buildsrc

object Config {
    const val applicationId: String = "uz.otamurod.weather"
    const val packageNameApp: String = "uz.otamurod.weather"
    const val packageNameLibPresentation: String = "uz.otamurod.presentation"
    const val packageNameLibData: String = "uz.otamurod.data"
    const val packageNameLibDomain: String = "uz.otamurod.domain"

    object Build {
        const val versionCode: Int = 1
        const val versionName: String = "1.0.0"
    }

    object Sdk {
        const val min = 21
        const val target = 34
        const val compile = 34
    }

    object API {
        const val OPEN_METEO_API_BASE_URL = "\"https://api.open-meteo.com/v1/\""
        const val GEO_CODING_API_BASE_URL = "\"https://geocoding-api.open-meteo.com/v1/\""
    }

    object SharedPreferences {
        const val SHARED_PREF_NAME = "\"WEATHER_APP_SHARED_PREFERENCES\""
    }
}