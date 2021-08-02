package pro.inmost.android.visario.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCase
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore

class SettingsViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel(), KoinComponent {
    private val credentials: CredentialsStore by inject()

    fun logout(){
        viewModelScope.launch {
            logoutUseCase.logout()
            credentials.clear()
        }
    }
}