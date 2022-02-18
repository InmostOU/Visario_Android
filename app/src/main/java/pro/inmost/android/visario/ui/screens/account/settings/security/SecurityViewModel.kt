package pro.inmost.android.visario.ui.screens.account.settings.security

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.ui.screens.auth.AppPreferences

class SecurityViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    val loginEachTime = MutableLiveData<Boolean>().apply {
        value = preferences.loginEachTime
    }

    fun toggleLoginEachTime(requestBiometrics: Boolean){
        preferences.loginEachTime = requestBiometrics
    }
}