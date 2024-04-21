package uz.otamurod.presentation.ui.weekly_forecast

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import uz.otamurod.domain.api.model.weather.Daily
import uz.otamurod.domain.model.DailyItem
import uz.otamurod.presentation.databinding.FragmentWeeklyForecastBinding
import uz.otamurod.presentation.ui.base.BaseFragment
import uz.otamurod.presentation.ui.weekly_forecast.adapter.WeekDaysRecyclerViewAdapter
import uz.otamurod.presentation.ui.weekly_forecast.adapter.WeeklyForecastRecyclerViewAdapter
import uz.otamurod.presentation.utils.weather.NavArgsKeys
import uz.otamurod.presentation.utils.weather.WeatherType

@AndroidEntryPoint
class WeeklyForecastFragment : BaseFragment() {
    private val binding by viewBinding(FragmentWeeklyForecastBinding::inflate)
    private var weeklyForecast: Daily? = null
    private val weekDaysRecyclerViewAdapter by lazy { WeekDaysRecyclerViewAdapter() }
    private val weeklyForecastRecyclerViewAdapter by lazy { WeeklyForecastRecyclerViewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            weeklyForecast = it.getSerializable(NavArgsKeys.WEEKLY_FORECAST_BUNDLE_KEY) as Daily
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerViews()

        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                delay(2000)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        weeklyForecast?.let {
            updateUI(it)
        }
    }

    private fun updateUI(forecast: Daily) {
        val dailyItemList = forecast.time.indices.map { index ->
            DailyItem(
                time = forecast.time[index],
                weatherCode = forecast.weatherCode[index],
                temperature2mMax = forecast.temperature2mMax[index],
                temperature2mMin = forecast.temperature2mMin[index],
                sunrise = forecast.sunrise[index],
                sunset = forecast.sunset[index],
                rainSum = forecast.rainSum[index],
                precipitationProbabilityMax = forecast.precipitationProbabilityMax[index],
                windSpeed10mMax = forecast.windSpeed10mMax[index]
            )
        }
        if (dailyItemList.isNotEmpty()) {
            weekDaysRecyclerViewAdapter.differ.submitList(dailyItemList)
            updateWeekDaysWeatherRecyclerViewVisibility(true)

            weeklyForecastRecyclerViewAdapter.differ.submitList(dailyItemList)
            updateWeeklyWeatherForecastRecyclerViewVisibility(true)

            val currentDateFormatted = DateTime.now().toString("yyyy-MM-dd")
            val todayWeatherForecast = dailyItemList.filter {
                it.time.startsWith(currentDateFormatted)
            }
            if (todayWeatherForecast.isNotEmpty()) {
                updateWeekDayWeatherCardInfo(todayWeatherForecast.first())
            }
        }
    }

    private fun setUpRecyclerViews() {
        // Week Days RecyclerView
        updateWeekDaysWeatherRecyclerViewVisibility(false)

        binding.weekDaysWeatherRecyclerView.adapter = weekDaysRecyclerViewAdapter
        weekDaysRecyclerViewAdapter.onClick = {
            updateWeekDayWeatherCardInfo(it)
        }

        // Weekly Weather Forecast RecyclerView
        updateWeeklyWeatherForecastRecyclerViewVisibility(false)
        binding.weeklyWeatherForecastRecyclerView.adapter = weeklyForecastRecyclerViewAdapter
        weeklyForecastRecyclerViewAdapter.onClick = {
            updateWeekDayWeatherCardInfo(it)
        }
    }

    private fun updateWeekDayWeatherCardInfo(dailyItem: DailyItem) {
        binding.apply {
            val weatherType =
                WeatherType.fromWMO(dailyItem.weatherCode)
            try {
                weekDayWeatherCodeAnimation.setAnimation(weatherType.animationAssetFileName)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "onBind: ${weatherType.animationAssetFileName}")
            }
            weekDayWeatherCodeAnimation.playAnimation()

            weekDayWeatherCodeTextView.text = getString(weatherType.weatherDescription)
            weekDayTemperatureTextView.text = String.format(
                "%d/%d °C",
                dailyItem.temperature2mMin.toInt(),
                dailyItem.temperature2mMax.toInt()
            )

            precipitationProbabilityTextView.text = String.format(
                "%d %s",
                dailyItem.precipitationProbabilityMax,
                "%"
            )

            windSpeedTextView.text = String.format(
                "%.1f km/h",
                dailyItem.windSpeed10mMax
            )

            rainSumTextView.text = String.format(
                "%.1f mm",
                dailyItem.rainSum
            )
        }
    }

    private fun updateWeekDaysWeatherRecyclerViewVisibility(isVisible: Boolean) {
        binding.weekDaysWeatherRecyclerView.isVisible = isVisible
    }

    private fun updateWeeklyWeatherForecastRecyclerViewVisibility(isVisible: Boolean) {
        binding.weekDaysWeatherRecyclerView.isVisible = isVisible
    }

    companion object {
        private const val TAG = "WeeklyForecastFragment"
    }
}