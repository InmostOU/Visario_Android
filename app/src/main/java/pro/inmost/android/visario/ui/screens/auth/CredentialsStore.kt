package pro.inmost.android.visario.ui.screens.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import pro.inmost.android.visario.BuildConfig
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.usecases.auth.credentials.UpdateCredentialsUseCase
import pro.inmost.android.visario.ui.utils.log

class CredentialsStore(context: Context, private val updateCredentialsUseCase: UpdateCredentialsUseCase) {
    private val preferences =  context.getSharedPreferences("credentials", MODE_PRIVATE)

   fun update(){
       updateCredentialsUseCase.update(getCredentials())
   }

    fun getCredentials(): Credentials {
        return Credentials(
            currentUser = getCurrentUser(),
            accessToken = getAccessToken(),
            refreshToken = getRefreshToken()
        )
    }

    fun saveCredentials(credentials: Credentials){
        log("credentials: $credentials")

        credentials.apply {
            saveAccessToken(accessToken)
            saveRefreshToken(refreshToken)
            saveCurrentUser(currentUser)
        }
        update()
    }

    fun isCredentialsNotEmpty() =
        getCurrentUser().isNotBlank() && getAccessToken().isNotBlank() && getRefreshToken().isNotBlank()

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
        preferences.edit {
            putString(PREF_KEY_ACCESS_TOKEN, token)
        }
    }

    private fun saveRefreshToken(token: String) {
        preferences.edit {
            putString(PREF_KEY_REFRESH_TOKEN, token)
        }
    }

    private fun saveCurrentUser(user: String) {
        preferences.edit {
            putString(PREF_KEY_USER, user)
        }
    }

    companion object{
        const val PREF_KEY_ACCESS_TOKEN = "${BuildConfig.APPLICATION_ID}.AccessTokenPref"
        const val PREF_KEY_REFRESH_TOKEN = "${BuildConfig.APPLICATION_ID}.RefreshTokenPref"
        const val PREF_KEY_USER = "${BuildConfig.APPLICATION_ID}.UserPref"
    }
}

