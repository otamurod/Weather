package uz.otamurod.domain.preferences

interface WeatherApplicationPreferencesApi {
    /**
     * Settings
     */

    var isDarkThemeEnabled: Boolean

    // Language Settings
    var appLanguageCode: String?

    // Location Settings
    var isAutoDetectLocationEnabled: Boolean
}