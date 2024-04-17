package uz.otamurod.specs.buildconfig

interface WeatherBuildConfigApi {
    val openMateoApiBaseUrl:String

    val geoCodingApiBaseUrl:String

    val buildVersion: String

    val sharedPreferencesName: String
}