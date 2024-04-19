package uz.otamurod.presentation.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.otamurod.presentation.databinding.FragmentWelcomeBinding
import uz.otamurod.presentation.ui.base.BaseFragment

@AndroidEntryPoint
class WelcomeFragment : BaseFragment() {
    private val binding by viewBinding(FragmentWelcomeBinding::inflate)
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val TAG = "WelcomeFragment"
    }
}