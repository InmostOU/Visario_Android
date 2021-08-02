package pro.inmost.android.visario.ui.screens.auth

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import pro.inmost.android.visario.ui.main.dataStore
import pro.inmost.android.visario.ui.utils.PREF_KEY_ACCESS_TOKEN
import pro.inmost.android.visario.ui.utils.PREF_KEY_REFRESH_TOKEN
import pro.inmost.android.visario.ui.utils.PREF_KEY_USER

class CredentialsStore(context: Context) {
    private val store = context.dataStore
    val data = store.data

    suspend fun clear(){
        store.edit { it.clear() }
    }

    suspend fun update(pref: (MutablePreferences) -> Unit) = store.edit {
        pref(it)
    }
}

fun MutablePreferences.updateUser(user: String?) {
    this[stringPreferencesKey(PREF_KEY_USER)] = user ?: ""
}

fun MutablePreferences.updateAccessToken(token: String?){
    this[stringPreferencesKey(PREF_KEY_ACCESS_TOKEN)] = token ?: ""
}

fun MutablePreferences.updateRefreshToken(token: String?){
    this[stringPreferencesKey(PREF_KEY_REFRESH_TOKEN)] = token ?: ""
}