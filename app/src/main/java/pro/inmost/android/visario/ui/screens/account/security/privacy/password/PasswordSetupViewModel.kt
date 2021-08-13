package pro.inmost.android.visario.ui.screens.account.security.privacy.password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase

class PasswordSetupViewModel(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    val currentPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val confirmNewPassword = MutableLiveData<String>()

    fun updatePassword(){

    }
}