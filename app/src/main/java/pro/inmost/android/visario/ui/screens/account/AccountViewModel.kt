package pro.inmost.android.visario.ui.screens.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCase
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.ui.entities.ProfileUI
import pro.inmost.android.visario.ui.entities.Viewers
import pro.inmost.android.visario.ui.entities.toUIProfile
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.utils.DateParser
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class AccountViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val credentialsStore: CredentialsStore,
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel() {
    private val _logout = SingleLiveEvent<Unit>()
    val logout: LiveData<Unit> = _logout
    val profile = MutableLiveData<ProfileUI>()

    fun loadProfile() {
       viewModelScope.launch {
            fetchProfileUseCase.fetch().onSuccess {
                profile.value = it.toUIProfile()
            }
        }
    }

    fun logout() {
        credentialsStore.clear()
        viewModelScope.launch {
            logoutUseCase.logout()
            _logout.call()
        }
    }

    fun changePhoto(){

    }
}