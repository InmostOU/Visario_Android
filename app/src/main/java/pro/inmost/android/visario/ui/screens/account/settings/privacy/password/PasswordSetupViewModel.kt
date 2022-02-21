package pro.inmost.android.visario.ui.screens.account.settings.privacy.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase
import pro.inmost.android.visario.ui.screens.auth.Validator
import pro.inmost.android.visario.utils.SingleLiveEvent

class PasswordSetupViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val validator = Validator()
    val currentPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val confirmNewPassword = MutableLiveData<String>()

    private val _currentPasswordError = MutableLiveData<Int>()
    val currentPasswordError: LiveData<Int> = _currentPasswordError
    val newPasswordError: LiveData<Int> = validator.passInvalid
    val matchingPasswordError: LiveData<Int> = validator.confirmPassInvalid

    private val _showMessage = SingleLiveEvent<String>()
    val showMessage: LiveData<String> = _showMessage

    private val _updatePasswordSuccess = SingleLiveEvent<Unit>()
    val updatePasswordSuccess: LiveData<Unit> = _updatePasswordSuccess

    fun updatePassword(){
        val currPass = currentPassword.value ?: ""
        val newPass = newPassword.value ?: ""
        val matchingPass = newPassword.value ?: ""

        if (validate(currPass, newPass, matchingPass)){
            viewModelScope.launch {
                updateProfileUseCase.updatePassword(currPass, newPass, matchingPass).onSuccess {
                    _updatePasswordSuccess.call()
                }.onFailure {
                    it.message?.let { message ->
                        _showMessage.value = message
                    }
                }
            }
        }
    }

    private fun validate(currPass: String, newPass: String, matchingPass: String): Boolean {
        if (currPass.isBlank()){
            _currentPasswordError.value = R.string.empty_field
            return false
        }
        return validator.validatePassword(newPass, matchingPass)
    }
}