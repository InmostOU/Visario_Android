package pro.inmost.android.visario.ui.screens.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import pro.inmost.android.visario.BuildConfig
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.usecases.auth.UpdateCredentialsUseCase
import pro.inmost.android.visario.utils.log


/**
 * Helper class to store user's [Credentials] in SharedPreferences
 *
 * @property updateCredentialsUseCase
 * @constructor
 *
 * @param context
 */
class CredentialsStore(context: Context, private val updateCredentialsUseCase: UpdateCredentialsUseCase) {
    private val preferences =  context.getSharedPreferences("credentials", MODE_PRIVATE)

    /**
     * Update credentials
     *
     */
    fun update(){
       updateCredentialsUseCase.update(getCredentials())
   }

    /**
     * Get credentials
     *
     * @return [Credentials]
     */
    fun getCredentials(): Credentials {
        return Credentials(
            currentUser = getCurrentUser(),
            accessToken = getAccessToken(),
            refreshToken = getRefreshToken()
        ).also {
            log("credentials: $it")
        }
    }

    /**
     * Save credentials into SharedPreferences
     *
     * @param credentials [Credentials]
     */
    fun saveCredentials(credentials: Credentials){
        credentials.apply {
            saveAccessToken(accessToken)
            saveRefreshToken(refreshToken)
            saveCurrentUser(currentUser)
        }
        updateCredentialsUseCase.update(credentials)
    }

    /**
     * Check if credentials not empty
     *
     */
    fun isCredentialsNotEmpty() =
        getCurrentUser().isNotBlank() && getAccessToken().isNotBlank() && getRefreshToken().isNotBlank()

    /**
     * Clear credentials in SharedPreferences
     *
     */
    fun clear() {
        preferences.edit {
            clear()
        }
        update()
    }

    private fun getCurrentUser(): String = preferences.getString(PREF_KEY_USER, "") ?: ""

    private fun getAccessToken() = preferences.getString(PREF_KEY_ACCESS_TOKEN, "") ?: ""

    private fun getRefreshToken() = preferences.getString(PREF_KEY_REFRESH_TOKEN, "") ?: ""

    private fun saveAccessToken(token: String) {
        preferences.edit(commit = true) {
            putString(PREF_KEY_ACCESS_TOKEN, token)
        }
    }

    private fun saveRefreshToken(token: String) {
        preferences.edit(commit = true) {
            putString(PREF_KEY_REFRESH_TOKEN, token)
        }
    }

    private fun saveCurrentUser(user: String) {
        preferences.edit(commit = true) {
            putString(PREF_KEY_USER, user)
        }
    }

    companion object{
        const val PREF_KEY_ACCESS_TOKEN = "${BuildConfig.APPLICATION_ID}.AccessTokenPref"
        const val PREF_KEY_REFRESH_TOKEN = "${BuildConfig.APPLICATION_ID}.RefreshTokenPref"
        const val PREF_KEY_USER = "${BuildConfig.APPLICATION_ID}.UserPref"
    }
}

