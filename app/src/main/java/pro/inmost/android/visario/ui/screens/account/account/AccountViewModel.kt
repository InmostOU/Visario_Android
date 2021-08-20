package pro.inmost.android.visario.ui.screens.account.account

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCase
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase
import pro.inmost.android.visario.ui.entities.profile.toUIProfile
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import pro.inmost.android.visario.ui.utils.extensions.toast
import java.io.File

class AccountViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val credentialsStore: CredentialsStore,
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val _logout = SingleLiveEvent<Unit>()
    val logout: LiveData<Unit> = _logout

    val username = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val birthdate = MutableLiveData<String>()
    val about = MutableLiveData<String>()
    val image = MutableLiveData<String>()


    fun loadProfile() {
       viewModelScope.launch {
            fetchProfileUseCase.fetch().map { it.toUIProfile() }.collect {
                username.value = it.username
                fullName.value = it.fullName
                email.value = it.email
                phoneNumber.value = it.phoneNumber
                birthdate.value = it.birthdateFormat
                about.value = it.about
                if (image.value != it.image) {
                    image.value = it.image
                }
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

    fun changePhoto(context: Context, uri: Uri){
        viewModelScope.launch {
            val file = File(context.filesDir.absolutePath.removeSuffix("files") + uri.path)
            updateProfileUseCase.uploadImage(file).onFailure {
                context.toast(R.string.upload_failed)
            }
        }
    }
}