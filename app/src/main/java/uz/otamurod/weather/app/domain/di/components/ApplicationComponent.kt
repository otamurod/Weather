package uz.otamurod.weather.app.domain.di.components

import dagger.BindsInstance
import dagger.Component
import uz.otamurod.data.di.modules.*
import uz.otamurod.presentation.MainActivity
import uz.otamurod.weather.app.WeatherApplication
import uz.otamurod.weather.app.domain.di.BuildModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        BuildModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        InteractorModule::class,
        DatabaseModule::class,
        SharedPreferencesModule::class
    ]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance weatherApplication: WeatherApplication): ApplicationComponent
    }

    fun inject(weatherApplication: WeatherApplication)
    fun inject(mainActivity: MainActivity)
}