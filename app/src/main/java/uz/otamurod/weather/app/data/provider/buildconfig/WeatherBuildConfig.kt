package uz.otamurod.weather.app.data.provider.buildconfig

import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi
import uz.otamurod.weather.BuildConfig

class WeatherBuildConfig : WeatherBuildConfigApi {
    override val openMateoApiBaseUrl: String
        get() = BuildConfig.OPEN_METEO_API_BASE_URL

    override val geoCodingApiBaseUrl: String
        get() = BuildConfig.GEO_CODING_API_BASE_URL

    override val buildVersion: String
        get() = BuildConfig.BUILD_VERSION

    override val sharedPreferencesName: String
        get() = BuildConfig.SHARED_PREF_NAME
}