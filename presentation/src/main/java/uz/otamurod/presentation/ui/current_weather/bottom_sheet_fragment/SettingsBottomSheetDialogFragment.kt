package uz.otamurod.presentation.ui.current_weather.bottom_sheet_fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.otamurod.domain.preferences.WeatherApplicationPreferencesApi
import uz.otamurod.presentation.R
import uz.otamurod.presentation.databinding.FragmentSettingsBottomSheetDialogBinding
import javax.inject.Inject

@AndroidEntryPoint
class SettingsBottomSheetDialogFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var preferences: WeatherApplicationPreferencesApi
    private lateinit var binding: FragmentSettingsBottomSheetDialogBinding
    private var onLanguageClickListener: OnLanguageClickListener? = null

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    @SuppressLint("MissingSuperCall")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomSheet = (requireView().parent as View)
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBottomSheetDialogBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpThemeSwitcher()
        setUpLanguageSwitcher()
    }

    private fun setUpThemeSwitcher() {
        binding.darkThemeSwitch.isChecked = preferences.isDarkThemeEnabled

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            preferences.isDarkThemeEnabled = isChecked
            requireActivity().recreate()
        }
    }

    private fun setUpLanguageSwitcher() {
        if (::preferences.isInitialized && preferences.appLanguageCode != null) {
            updateLanguageCheckMark(preferences.appLanguageCode!!)
        }

        binding.languageUzbekTextView.setOnClickListener {
            onLanguageClickListener?.let {
                updateLanguageCheckMark("uz")
                it.onLanguageSelected("uz")
            }
        }

        binding.languageRussianTextView.setOnClickListener {
            onLanguageClickListener?.let {
                updateLanguageCheckMark("ru")
                it.onLanguageSelected("ru")
            }
        }

        binding.languageEnTextView.setOnClickListener {
            onLanguageClickListener?.let {
                updateLanguageCheckMark("en")
                it.onLanguageSelected("en")
            }
        }
    }

    fun setOnLanguageClickListener(onLanguageClickListener: OnLanguageClickListener) {
        this.onLanguageClickListener = onLanguageClickListener
    }

    private fun updateLanguageCheckMark(languageCode: String) {
        when (languageCode) {
            "uz" -> {
                tickCheckMark(isUzbek = true, isRussian = false, isEnglish = false)
            }
            "ru" -> {
                tickCheckMark(isUzbek = false, isRussian = true, isEnglish = false)
            }
            "en" -> {
                tickCheckMark(isUzbek = false, isRussian = false, isEnglish = true)
            }
        }
    }

    private fun tickCheckMark(isUzbek: Boolean, isRussian: Boolean, isEnglish: Boolean) {
        if (isUzbek) {
            binding.languageUzbekTextView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.uz_flag,
                0,
                R.drawable.ic_check_mark,
                0
            )
        } else {
            binding.languageUzbekTextView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.uz_flag,
                0,
                0,
                0
            )
        }

        if (isRussian) {
            binding.languageRussianTextView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ru_flag,
                0,
                R.drawable.ic_check_mark,
                0
            )
        } else {
            binding.languageRussianTextView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ru_flag,
                0,
                0,
                0
            )
        }

        if (isEnglish) {
            binding.languageEnTextView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.uk_flag,
                0,
                R.drawable.ic_check_mark,
                0
            )
        } else {
            binding.languageEnTextView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.uk_flag,
                0,
                0,
                0
            )
        }
    }

    interface OnLanguageClickListener {
        fun onLanguageSelected(languageCode: String)
    }

    companion object {
        private const val TAG = "SettingsBottomSheet"
    }
}