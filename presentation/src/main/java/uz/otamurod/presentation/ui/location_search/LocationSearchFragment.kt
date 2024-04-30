package uz.otamurod.presentation.ui.location_search

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.util.DataState
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.FragmentLocationSearchBinding
import uz.otamurod.presentation.ui.base.BaseFragment
import uz.otamurod.presentation.ui.location_search.adapter.LocationSearchResultsRecyclerViewAdapter
import uz.otamurod.presentation.ui.location_search.adapter.SearchedLocationsRecyclerViewAdapter
import uz.otamurod.presentation.utils.network.NetworkStatusListener
import uz.otamurod.presentation.utils.weather.NavArgsKeys
import java.util.*

@AndroidEntryPoint
class LocationSearchFragment : BaseFragment(), NetworkStatusListener {
    private val binding by viewBinding(FragmentLocationSearchBinding::inflate)
    private val viewModel: LocationSearchViewModel by viewModels()
    private val locationSearchResultsRecyclerViewAdapter by lazy { LocationSearchResultsRecyclerViewAdapter() }
    private val searchedLocationsRecyclerViewAdapter by lazy { SearchedLocationsRecyclerViewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.setContext(requireActivity(), this@LocationSearchFragment)
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set requested orientation
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setUpSearchView()
        setUpObservers()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        // Location Search Results RecyclerView
        updateSearchResultsRvVisibility(false)

        binding.locationSearchResultsRecyclerView.adapter = locationSearchResultsRecyclerViewAdapter
        locationSearchResultsRecyclerViewAdapter.onClick = {
            updateSearchResultsRvVisibility(false)
            lifecycleScope.launch {
                viewModel.saveSearchedPlace(place = it)
                delay(3000)
                setFragmentResultArg(it)
            }
        }

        // Searched Locations RecyclerView
        updateSearchedLocationsRvVisibility(false)
        binding.searchedLocationsRecyclerView.adapter = searchedLocationsRecyclerViewAdapter
        searchedLocationsRecyclerViewAdapter.onClick = {
            lifecycleScope.launch {
                viewModel.saveSearchedPlace(place = it)
                delay(500)
                setFragmentResultArg(it)
            }
        }

        searchedLocationsRecyclerViewAdapter.onDeleteClick = {
            lifecycleScope.launch {
                viewModel.deleteSearchedPlaceById(it)
            }
        }
    }

    private fun setFragmentResultArg(place: Place) {
        lifecycleScope.launch {
            val bundle = Bundle().apply {
                putInt(
                    NavArgsKeys.SEARCHED_PLACE_ID,
                    place.id
                )
            }
            requireActivity().supportFragmentManager.setFragmentResult(
                NavArgsKeys.SEARCH_PLACE_REQUEST_KEY,
                bundle
            )
            findNavController().navigateUp()
        }
    }

    private fun setUpObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    updateProgressBarVisibility(true)
                }
                is DataState.Success -> {
                    updateProgressBarVisibility(false)
                }
                is DataState.Error -> {
                    updateProgressBarVisibility(false)
                    val errorMessage =
                        String.format("%s: %s", getString(R.string.error), it.message)
                    Toast.makeText(
                        requireContext(), errorMessage, Toast.LENGTH_LONG
                    ).show()
                    Log.d(TAG, "setUpObservers: $errorMessage")
                }
            }
        }

        viewModel.isProcessing.observe(viewLifecycleOwner) {
            if (it) {
                updateProgressBarVisibility(true)
            } else {
                updateProgressBarVisibility(false)
            }
        }

        viewModel.locationSearchResult.observe(viewLifecycleOwner) {
            if (it.places.isNotEmpty()) {
                locationSearchResultsRecyclerViewAdapter.differ.submitList(it.places)
                updateSearchResultsRvVisibility(true)
            }
        }

        viewModel.searchedPlaces.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                searchedLocationsRecyclerViewAdapter.differ.submitList(it)
                updateSearchedLocationsRvVisibility(true)
            } else {
                updateSearchedLocationsRvVisibility(false)
            }
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

    private fun updateProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    private fun updateSearchResultsRvVisibility(isVisible: Boolean) {
        binding.locationSearchResultsRecyclerView.isVisible = isVisible
    }

    private fun updateSearchedLocationsRvVisibility(isVisible: Boolean) {
        binding.searchedLocationsRecyclerView.isVisible = isVisible
    }

    private fun setUpSearchView() {
        binding.locationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                binding.locationSearchView.clearFocus()
                binding.locationSearchView.setQuery("", false)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    updateSearchResultsRvVisibility(false)
                }
                return true
            }
        })
    }

    private fun performSearch(query: String?) {
        /**
         * Perform a network call to get locations
         * To get search location data that fits app language, we pass [appLanguageCode] from Settings
         */
        if (!query.isNullOrEmpty()) {
            lifecycleScope.launch {
                viewModel.searchLocation(name = query, language = preferences.appLanguageCode!!)
            }
        }
    }

    override fun onNetworkAvailable() {
        viewModel.updateNetworkStatus(true)
    }

    override fun onNetworkLost() {
        viewModel.updateNetworkStatus(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    companion object {
        private const val TAG = "LocationSearchFragment"
    }
}