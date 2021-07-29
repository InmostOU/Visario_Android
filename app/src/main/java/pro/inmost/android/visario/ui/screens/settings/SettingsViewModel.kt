package pro.inmost.android.visario.ui.screens.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.ui.main.dataStore
import pro.inmost.android.visario.ui.boundaries.AccountRepository
import pro.inmost.android.visario.ui.utils.PREF_KEY_ACCESS_TOKEN
import pro.inmost.android.visario.ui.utils.PREF_KEY_REFRESH_TOKEN

class SettingsViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    fun logout(context: Context){
        viewModelScope.launch {
            accountRepository.logout()
            removeTokensFromDataStore(context)
        }
    }

    private suspend fun removeTokensFromDataStore(context: Context) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(PREF_KEY_ACCESS_TOKEN)] = ""
            pref[stringPreferencesKey(PREF_KEY_REFRESH_TOKEN)] = ""
        }
    }
}