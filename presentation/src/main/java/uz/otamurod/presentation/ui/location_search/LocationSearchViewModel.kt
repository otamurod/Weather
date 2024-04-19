package uz.otamurod.presentation.ui.location_search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val locationSearchInteractorApi: LocationSearchInteractorApi
) : ViewModel() {

}