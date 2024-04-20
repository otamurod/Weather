package uz.otamurod.presentation.ui.current_weather

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uz.otamurod.domain.model.LastLocation
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.FragmentCurrentWeatherBinding
import uz.otamurod.presentation.ui.base.BaseFragment

@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment() {
    private val binding by viewBinding(FragmentCurrentWeatherBinding::inflate)
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private var lastLocation: LastLocation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            lastLocation = it.getSerializable("lastLocation") as LastLocation
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
        setUpFragmentResultListener()

        Log.d(TAG, "onViewCreated: $lastLocation")
        Toast.makeText(requireContext(), "${lastLocation?.addressName}", Toast.LENGTH_LONG).show()
    }

    private fun setUpFragmentResultListener() {
        setFragmentResultListener("placeResult") { _, result ->
            val placeId = result.getLong("placeId", -1L) // Default value in case of absence
            // Use placeId to update UI or perform other operations
            if (placeId != -1L) {
                lifecycleScope.launch {
                    viewModel.getSearchedPlaceById(placeId.toInt())
                }
            }
        }
    }

    private fun setUpClickListeners() {
        binding.filterIconCard.setOnClickListener {
            Log.d(TAG, "filterIconCard: click")
            displayBottomSheetDialogFragment()
        }

        binding.goToSearchLocation.setOnClickListener {
            Log.d(TAG, "goToSearchLocation: click")

            // navigating to itself
            findNavController().navigate(R.id.locationSearchFragment)
        }

        binding.nextSevenDaysTextView.setOnClickListener {
            Log.d(TAG, "nextSevenDaysTextView: click")
            // navigating to itself
            findNavController().navigate(R.id.weeklyForecastFragment)
        }
    }

    private fun displayBottomSheetDialogFragment() {
        Toast.makeText(requireContext(), "displayBottomSheetDialogFragment", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        private const val TAG = "CurrentWeatherFragment"
    }
}