package uz.otamurod.presentation.ui.weekly_forecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.otamurod.presentation.databinding.FragmentCurrentWeatherBinding
import uz.otamurod.presentation.ui.base.BaseFragment
import uz.otamurod.presentation.ui.current_weather.CurrentWeatherViewModel

@AndroidEntryPoint
class WeeklyForecastFragment : BaseFragment() {
    private val binding by viewBinding(FragmentCurrentWeatherBinding::inflate)
    private val viewModel: CurrentWeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val TAG = "WeeklyForecastFragment"
    }
}