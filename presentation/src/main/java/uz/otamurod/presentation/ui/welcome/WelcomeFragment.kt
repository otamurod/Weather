package uz.otamurod.presentation.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.FragmentWelcomeBinding
import uz.otamurod.presentation.ui.base.BaseFragment
import uz.otamurod.presentation.utils.network.NetworkStatusListener

@AndroidEntryPoint
class WelcomeFragment : BaseFragment(), NetworkStatusListener {
    private val binding by viewBinding(FragmentWelcomeBinding::inflate)
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.setContext(requireActivity(), this@WelcomeFragment, preferences)
        }
        checkAndRequestPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSwipeRefreshLayout()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.grantLocationPermissionButton.setOnClickListener {
            viewModel.requestLocationPermission()
        }
    }

    private fun setUpObservers() {
        viewModel.appVersionName.observe(viewLifecycleOwner) {
            binding.versionNameTextView.text =
                String.format("%s %s", getString(R.string.version), it)
        }

        viewModel.lastLocation.observe(viewLifecycleOwner) { lastLocation ->
            if (lastLocation == null) {
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                // navigate to current weather fragment and pass last location as argument
                lifecycleScope.launch {
                    updateShowGpsEnablePromptVisibility(false)
                    updateTurnOnNetworkVisibility(false)

                    /**
                     * Delay 3 seconds so that our Welcome Screen act as a Splash Screen
                     */
                    delay(3000)
                    findNavController().navigate(R.id.currentWeatherFragment)
                }
            }
        }

        viewModel.shouldShowGpsEnablePrompt.observe(viewLifecycleOwner) { gpsEnableFlag ->
            gpsEnableFlag?.let {
                updateShowGpsEnablePromptVisibility(it)
            }
        }

        viewModel.shouldShowTurnOnNetworkPrompt.observe(viewLifecycleOwner) { turnOnNetworkFlag ->
            turnOnNetworkFlag?.let {
                updateTurnOnNetworkVisibility(it)
            }
        }

        viewModel.shouldShowGrantPermissionPrompt.observe(viewLifecycleOwner) { grantPermissionFlag ->
            grantPermissionFlag?.let {
                updateShowGrantPermissionVisibility(it)
            }
        }
    }

    private fun updateShowGpsEnablePromptVisibility(isVisible: Boolean) {
        binding.enableGpsPromptLayout.isVisible = isVisible
    }

    private fun updateTurnOnNetworkVisibility(isVisible: Boolean) {
        binding.turnOnNetworkPromptLayout.isVisible = isVisible
    }

    private fun updateShowGrantPermissionVisibility(isVisible: Boolean) {
        binding.grantLocationPermissionPromptLayout.isVisible = isVisible
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
                viewModel.refreshLastLocation()
            }
        }
    }

    override fun onNetworkAvailable() {
        viewModel.updateNetworkStatus(true)
    }

    override fun onNetworkLost() {
        viewModel.updateNetworkStatus(false)
    }

    private fun checkAndRequestPermission() {
        viewModel.permissionStateLiveData.value?.let {
            if (!it) {
                viewModel.requestLocationPermission()
            }
        }
    }

    companion object {
        private const val TAG = "WelcomeFragment"
    }
}