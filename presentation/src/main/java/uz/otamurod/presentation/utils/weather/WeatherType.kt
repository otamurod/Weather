package uz.otamurod.presentation.utils.weather

import uz.otamurod.presentation.R

sealed class WeatherType(
    val weatherDescription: Int,
    val animationAssetFileName: String
) {
    object ClearSky : WeatherType(
        weatherDescription = R.string.clear_sky,
        animationAssetFileName = "clear_sky.json"
    )

    object MainlyClear : WeatherType(
        weatherDescription = R.string.mainly_clear,
        animationAssetFileName = "mainly_clear.json"
    )

    object PartlyCloudy : WeatherType(
        weatherDescription = R.string.partly_cloudy,
        animationAssetFileName = "partly_cloudy.json"
    )

    object Overcast : WeatherType(
        weatherDescription = R.string.overcast,
        animationAssetFileName = "overcast.json"
    )

    object Foggy : WeatherType(
        weatherDescription = R.string.foggy,
        animationAssetFileName = "foggy.json"
    )

    object DepositingRimeFog : WeatherType(
        weatherDescription = R.string.depositing_rime_fog,
        animationAssetFileName = "depositing_rime_fog.json"
    )

    object LightDrizzle : WeatherType(
        weatherDescription = R.string.light_drizzle,
        animationAssetFileName = "light_drizzle.json"
    )

    object ModerateDrizzle : WeatherType(
        weatherDescription = R.string.moderate_drizzle,
        animationAssetFileName = "moderate_drizzle.json"
    )

    object DenseDrizzle : WeatherType(
        weatherDescription = R.string.dense_drizzle,
        animationAssetFileName = "dense_drizzle.json"
    )

    object LightFreezingDrizzle : WeatherType(
        weatherDescription = R.string.light_freezing_drizzle,
        animationAssetFileName = "light_freezing_drizzle.json"
    )

    object DenseFreezingDrizzle : WeatherType(
        weatherDescription = R.string.dense_freezing_drizzle,
        animationAssetFileName = "dense_freezing_drizzle.json"
    )

    object SlightRain : WeatherType(
        weatherDescription = R.string.slight_rain,
        animationAssetFileName = "slight_rain.json"
    )

    object ModerateRain : WeatherType(
        weatherDescription = R.string.moderate_rain,
        animationAssetFileName = "moderate_rain.json"
    )

    object HeavyRain : WeatherType(
        weatherDescription = R.string.heavy_rain,
        animationAssetFileName = "heavy_rain.json"
    )

    object LightFreezingRain : WeatherType(
        weatherDescription = R.string.light_freezing_rain,
        animationAssetFileName = "light_freezing_rain.json"
    )

    object HeavyFreezingRain : WeatherType(
        weatherDescription = R.string.heavy_freezing_rain,
        animationAssetFileName = "heavy_freezing_rain.json"
    )

    object SlightSnowFall : WeatherType(
        weatherDescription = R.string.slight_snow_fall,
        animationAssetFileName = "slight_snow_fall.json"
    )

    object ModerateSnowFall : WeatherType(
        weatherDescription = R.string.moderate_snow_fall,
        animationAssetFileName = "moderate_snow_fall.json"
    )

    object HeavySnowFall : WeatherType(
        weatherDescription = R.string.heavy_snow_fall,
        animationAssetFileName = "heavy_snow_fall.json"
    )

    object SnowGrains : WeatherType(
        weatherDescription = R.string.snow_grains,
        animationAssetFileName = "snow_grains.json"
    )

    object SlightRainShower : WeatherType(
        weatherDescription = R.string.slight_rain_shower,
        animationAssetFileName = "slight_rain.json"
    )

    object ModerateRainShower : WeatherType(
        weatherDescription = R.string.moderate_rain_shower,
        animationAssetFileName = "moderate_rain.json"
    )

    object ViolentRainShower : WeatherType(
        weatherDescription = R.string.violent_rain_shower,
        animationAssetFileName = "heavy_rain.json"
    )

    object SlightSnowShower : WeatherType(
        weatherDescription = R.string.slight_snow_shower,
        animationAssetFileName = "slight_snow_fall.json"
    )

    object HeavySnowShower : WeatherType(
        weatherDescription = R.string.heavy_snow_shower,
        animationAssetFileName = "moderate_snow_fall.json"
    )

    object Thunderstorm : WeatherType(
        weatherDescription = R.string.thunderstorm,
        animationAssetFileName = "thunderstorm.json"
    )

    object SlightThunderstormHail : WeatherType(
        weatherDescription = R.string.slight_thunderstorm_hail,
        animationAssetFileName = "thunderstorm.json"
    )

    object HeavyThunderstormHail : WeatherType(
        weatherDescription = R.string.heavy_thunderstorm_hail,
        animationAssetFileName = "heavy_thunderstorm_hail.json"
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingRain
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShower
                81 -> ModerateRainShower
                82 -> ViolentRainShower
                85 -> SlightSnowShower
                86 -> HeavySnowShower
                95 -> Thunderstorm
                96 -> SlightThunderstormHail
                99 -> HeavyThunderstormHail
                else -> ClearSky
            }
        }
    }
}
