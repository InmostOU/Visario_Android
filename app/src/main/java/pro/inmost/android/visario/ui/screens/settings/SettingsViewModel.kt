package pro.inmost.android.visario.ui.screens.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pro.inmost.android.visario.domain.entities.Credentials
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCase
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class SettingsViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val credentialsStore: CredentialsStore
) : ViewModel() {
    private val _logout = SingleLiveEvent<Unit>()
    val logout: LiveData<Unit> = _logout

    fun logout() {
        credentialsStore.clear()
        viewModelScope.launch {
            logoutUseCase.logout()
            _logout.call()
        }
    }
}