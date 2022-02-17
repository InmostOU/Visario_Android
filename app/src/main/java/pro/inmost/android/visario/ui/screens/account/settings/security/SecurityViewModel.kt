package pro.inmost.android.visario.ui.screens.account.settings.security

import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.ui.screens.auth.AppPreferences

class SecurityViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    fun toggleBiometrics(requestBiometrics: Boolean){
        preferences.requestBiometrics = requestBiometrics
    }
}