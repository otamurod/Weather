package uz.otamurod.presentation.ui.current_weather

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.model.weather.Hourly
import uz.otamurod.domain.model.HourlyItem
import uz.otamurod.domain.model.LastLocation
import uz.otamurod.domain.util.DataState
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.FragmentCurrentWeatherBinding
import uz.otamurod.presentation.ui.base.BaseFragment
import uz.otamurod.presentation.ui.current_weather.adapter.HourlyWeatherRecyclerViewAdapter
import uz.otamurod.presentation.ui.current_weather.bottom_sheet_fragment.SettingsBottomSheetDialogFragment
import uz.otamurod.presentation.utils.network.NetworkStatusListener
import uz.otamurod.presentation.utils.weather.NavArgsKeys
import uz.otamurod.presentation.utils.weather.WeatherType
import java.util.*

@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment(), NetworkStatusListener {
    private val binding by viewBinding(FragmentCurrentWeatherBinding::inflate)
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private val hourlyWeatherRecyclerViewAdapter by lazy { HourlyWeatherRecyclerViewAdapter() }
    private var weatherForecast: Forecast? = null
    private lateinit var activity: LocalizationActivity
    private var isSettingsBottomSheetFragmentShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity() as LocalizationActivity

        lifecycleScope.launch {
            viewModel.setContext(requireActivity(), this@CurrentWeatherFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefreshLayout()
        setUpRecyclerView()
        setUpClickListeners()
        setUpObservers()
        setUpFragmentResultListener()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.indicatorColor1),
            ContextCompat.getColor(requireContext(), R.color.indicatorColor2),
            ContextCompat.getColor(requireContext(), R.color.indicatorColor3),
            ContextCompat.getColor(requireContext(), R.color.indicatorColor4),
            ContextCompat.getColor(requireContext(), R.color.indicatorColor5),
            ContextCompat.getColor(requireContext(), R.color.indicatorColor6)
        )

        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.getDeviceLocation()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.hourlyWeatherRecyclerView.adapter = hourlyWeatherRecyclerViewAdapter
        hourlyWeatherRecyclerViewAdapter.onClick = {

        }
        updateRecyclerViewVisibility(false)
    }

    private fun updateRecyclerViewVisibility(isVisible: Boolean) {
        binding.hourlyWeatherRecyclerView.isVisible = isVisible
    }

    private fun setUpObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    updateRefreshState(true)
                }
                is DataState.Success -> {
                    updateRefreshState(false)
                }
                is DataState.Error -> {
                    updateRefreshState(false)
                    val errorMessage =
                        String.format("%s: %s", getString(R.string.error), it.message)
                    Toast.makeText(
                        requireContext(), errorMessage, Toast.LENGTH_LONG
                    ).show()
                    Log.d(TAG, "setUpObservers: $errorMessage")
                }
            }
        }

        viewModel.forecast.observe(viewLifecycleOwner) {
            weatherForecast = it
            updateCurrentWeatherForecast(it)
        }

        viewModel.lastLocation.observe(viewLifecycleOwner) { lastLocation ->
            lastLocation?.let {
                updateLastLocationInfo(it)
                lifecycleScope.launch {
                    viewModel.getForecast(it.latitude, it.longitude)
                }
            }
        }

        viewModel.searchedPlace.observe(viewLifecycleOwner) {
            updateSearchedPlaceInfo(it)
            lifecycleScope.launch {
                viewModel.getForecast(it.latitude, it.longitude)
            }
        }

        viewModel.shouldShowTurnOnNetworkPrompt.observe(viewLifecycleOwner) { turnOnNetworkFlag ->
            turnOnNetworkFlag?.let {
                updateTurnOnNetworkVisibility(it)
            }
        }

        viewModel.isSettingsBottomSheetShown.observe(viewLifecycleOwner) {
            isSettingsBottomSheetFragmentShown = it
        }

        viewModel.isNetworkConnected.observe(viewLifecycleOwner) { isConnected ->
            lifecycleScope.launch {
                updateNetworkConnectionAlert(isConnected)
            }
        }
    }

    private suspend fun updateNetworkConnectionAlert(isConnected: Boolean) {
        binding.apply {
            val colorResId =
                if (isConnected) R.color.material_green_500 else R.color.material_red_500
            val messageResId =
                if (isConnected) R.string.network_connection_available else R.string.network_connection_unavailable

            networkConnectionAlert.backgroundTintList =
                AppCompatResources.getColorStateList(requireContext(), colorResId)
            networkConnectionAlert.text = getString(messageResId)
            networkConnectionAlert.isVisible = true
            delay(4000)
            networkConnectionAlert.isVisible = false
        }
    }

    private fun updateTurnOnNetworkVisibility(isVisible: Boolean) {
        binding.turnOnNetworkPromptLayout.isVisible = isVisible
    }

    private fun updateRefreshState(isRefreshing: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    private fun updateLastLocationInfo(location: LastLocation) {
        binding.apply {
            addressNameTextView.text = location.addressName
        }
    }

    private fun updateSearchedPlaceInfo(place: Place) {
        binding.apply {
            addressNameTextView.text = String.format("%s, %s", place.name, place.country)
        }
    }

    private fun updateCurrentWeatherForecast(forecast: Forecast) {
        binding.apply {
            val weatherType = WeatherType.fromWMO(forecast.current.weatherCode)
            try {
                lottieCurrentWeatherCodeAnimation.setAnimation(weatherType.animationAssetFileName)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(
                    TAG, "lottieCurrentWeatherCodeAnimation: ${weatherType.animationAssetFileName}"
                )
            }
            lottieCurrentWeatherCodeAnimation.playAnimation()

            currentDayAndDateTextView.text = formatDate(forecast.current.time)
            currentWeatherCodeTextView.text = getString(weatherType.weatherDescription)
            isDayTextView.text =
                if (forecast.current.isDay == 1) getString(R.string.is_day_true) else getString(R.string.is_day_false)

            currentTemperatureTextView.text = String.format(
                "%.1f %s", forecast.current.temperature2m, forecast.currentUnits.temperature2m
            )
            currentApparentTemperatureTextView.text = String.format(
                "%s %.1f %s",
                getString(R.string.current_apparent_temperature_label),
                forecast.current.apparentTemperature,
                forecast.currentUnits.apparentTemperature
            )

            val currentIndex = getCurrentDayIndex(forecast.daily.time)
            if (currentIndex != -1) {
                precipitationProbabilityTextView.text = String.format(
                    "%d %s",
                    forecast.daily.precipitationProbabilityMax[currentIndex],
                    forecast.dailyUnits.precipitationProbabilityMax
                )

                windSpeedTextView.text = String.format(
                    "%.1f %s",
                    forecast.daily.windSpeed10mMax[currentIndex],
                    forecast.dailyUnits.windSpeed10mMax
                )

                rainSumTextView.text = String.format(
                    "%.1f %s", forecast.daily.rainSum[currentIndex], forecast.dailyUnits.rainSum
                )

                val sunriseTime = extractHour(forecast.daily.sunrise[currentIndex])
                val sunsetTime = extractHour(forecast.daily.sunset[currentIndex])

                sunriseTimeTextView.text = sunriseTime
                sunsetTimeTextView.text = sunsetTime

                Glide.with(requireContext())
                    .load(ContextCompat.getDrawable(requireActivity(), R.drawable.sunrise_sunset))
                    .into(sunriseSunsetGif)
            }
        }

        val currentDateHourlyWeather = extractCurrentDateHourlyWeather(forecast.hourly)
        if (currentDateHourlyWeather.isNotEmpty()) {
            hourlyWeatherRecyclerViewAdapter.differ.submitList(currentDateHourlyWeather)
            updateRecyclerViewVisibility(true)
        }
    }

    private fun extractHour(dateTimeString: String): String {
        val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime = DateTime.parse(dateTimeString, dateTimeFormat)
        return dateTime.toString("HH:mm")
    }

    private fun extractCurrentDateHourlyWeather(hourly: Hourly): List<HourlyItem> {
        // Step 1: Convert current date to the format used in the time list
        val currentDateFormatted = DateTime.now().toString("yyyy-MM-dd") + "T"

        // Step 2: Filter the time list to find indices where the date matches the current date
        val filteredIndices = hourly.time.indices.filter {
            hourly.time[it].startsWith(currentDateFormatted)
        }

        // Step 3: Extract corresponding data based on filtered indices and create HourlyItem objects
        val hourlyItemList = filteredIndices.map { index ->
            HourlyItem(
                time = hourly.time[index],
                temperature2m = hourly.temperature2m[index],
                apparentTemperature = hourly.apparentTemperature[index],
                precipitationProbability = hourly.precipitationProbability[index],
                weatherCode = hourly.weatherCode[index]
            )
        }

        return hourlyItemList
    }

    private fun getCurrentDayIndex(dailyTime: List<String>): Int {
        val currentTime = DateTime.now().toString("yyyy-MM-dd")
        return dailyTime.indexOf(currentTime)
    }

    private fun formatDate(dateString: String): String {
        val dateTime = DateTime.parse(dateString)

        val dayOfWeek = dateTime.dayOfWeek().asText.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        val dayOfMonth = dateTime.dayOfMonth().asText
        val monthName = dateTime.monthOfYear().asText

        return "$dayOfWeek, $dayOfMonth-$monthName"
    }

    private fun setUpFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            NavArgsKeys.SEARCH_PLACE_REQUEST_KEY, viewLifecycleOwner
        ) { _, result ->
            val placeId = result.getInt(
                NavArgsKeys.SEARCHED_PLACE_ID, -1
            ) // Default value in case of absence
            if (placeId != -1) {
                lifecycleScope.launch {
                    viewModel.getSearchedPlaceById(placeId)
                }
            }
        }
    }

    private fun setUpClickListeners() {
        binding.filterIconCard.setOnClickListener {
            viewModel.updateBottomSheetShouldShowFlag(false)
            displayBottomSheetDialogFragment()
        }

        binding.goToSearchLocation.setOnClickListener {
            findNavController().navigate(R.id.locationSearchFragment)
        }

        binding.nextSevenDaysTextView.setOnClickListener {
            weatherForecast?.let {
                val bundle = Bundle().apply {
                    this.putSerializable(NavArgsKeys.WEEKLY_FORECAST_BUNDLE_KEY, it.daily)
                }
                findNavController().navigate(R.id.weeklyForecastFragment, bundle)
            }
        }
    }

    private fun displayBottomSheetDialogFragment() {
        val settingsBottomSheetDialogFragment = SettingsBottomSheetDialogFragment()

        settingsBottomSheetDialogFragment.setOnLanguageClickListener(object :
            SettingsBottomSheetDialogFragment.OnLanguageClickListener {
            override fun onLanguageSelected(languageCode: String) {
                preferences.appLanguageCode = languageCode
                changeLanguage(languageCode)

                settingsBottomSheetDialogFragment.dismiss()
            }
        })

        if (!isSettingsBottomSheetFragmentShown) {
            viewModel.updateBottomSheetShouldShowFlag(true)
            settingsBottomSheetDialogFragment.show(
                parentFragmentManager, settingsBottomSheetDialogFragment.tag
            )
        }
    }

    private fun changeLanguage(languageCode: String) {
        activity.setLanguage(languageCode)
    }

    override fun onNetworkAvailable() {
        viewModel.updateNetworkStatus(true)
    }

    override fun onNetworkLost() {
        viewModel.updateNetworkStatus(false)
    }

    companion object {
        private const val TAG = "CurrentWeatherFragment"
    }

}