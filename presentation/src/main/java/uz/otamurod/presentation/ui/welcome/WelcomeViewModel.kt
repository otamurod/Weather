package uz.otamurod.presentation.ui.welcome

import androidx.lifecycle.ViewModel
import com.markodevcic.peko.PermissionRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val permissionRequester: PermissionRequester,
    private val lastLocationInteractorApi: LastLocationInteractorApi
) : ViewModel() {

}