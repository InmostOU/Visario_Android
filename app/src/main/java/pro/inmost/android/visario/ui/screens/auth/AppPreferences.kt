package pro.inmost.android.visario.ui.screens.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import pro.inmost.android.visario.BuildConfig
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.usecases.auth.UpdateCredentialsUseCase
import pro.inmost.android.visario.utils.log


/**
 * Helper class to store app preferences (like a [Credentials]) in SharedPreferences
 *
 */
class AppPreferences(
    private val preferences: SharedPreferences,
    private val updateCredentialsUseCase: UpdateCredentialsUseCase
) {

    var requestBiometrics: Boolean
        get() = preferences.getBoolean(PREF_KEY_BIOMETRICS, false)
        set(value) {
            preferences.edit(commit = true) {
                putBoolean(PREF_KEY_BIOMETRICS, value)
            }
        }

    var accessToken: String
        get() = preferences.getString(PREF_KEY_ACCESS_TOKEN, "") ?: ""
        set(value) {
            preferences.edit(commit = true) {
                putString(PREF_KEY_ACCESS_TOKEN, value)
            }
        }

    var refreshToken: String
        get() = preferences.getString(PREF_KEY_REFRESH_TOKEN, "") ?: ""
        set(value) {
            preferences.edit(commit = true) {
                putString(PREF_KEY_REFRESH_TOKEN, value)
            }
        }

    var currentUser: String
        get() = preferences.getString(PREF_KEY_USER, "") ?: ""
        set(value) {
            preferences.edit(commit = true) {
                putString(PREF_KEY_USER, value)
            }
        }

    /**
     * Update credentials
     *
     */
    fun update() {
        updateCredentialsUseCase.update(getCredentials())
    }

    /**
     * Get credentials
     *
     * @return [Credentials]
     */
    fun getCredentials(): Credentials {
        return Credentials(
            currentUser = currentUser,
            accessToken = accessToken,
            refreshToken = refreshToken
        ).also {
            log("credentials: $it")
        }
    }

    /**
     * Save credentials into SharedPreferences
     *
     * @param credentials [Credentials]
     */
    fun saveCredentials(credentials: Credentials) {
        currentUser = credentials.currentUser
        accessToken = credentials.accessToken
        refreshToken = credentials.refreshToken
        updateCredentialsUseCase.update(credentials)
    }

    /**
     * Check if credentials not empty
     *
     */
    fun isCredentialsNotEmpty() =
        currentUser.isNotBlank() && accessToken.isNotBlank() && refreshToken.isNotBlank()

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

    companion object {
        const val PREF_KEY_ACCESS_TOKEN = "${BuildConfig.APPLICATION_ID}.AccessTokenPref"
        const val PREF_KEY_REFRESH_TOKEN = "${BuildConfig.APPLICATION_ID}.RefreshTokenPref"
        const val PREF_KEY_USER = "${BuildConfig.APPLICATION_ID}.UserPref"
        const val PREF_KEY_BIOMETRICS = "${BuildConfig.APPLICATION_ID}.Biometrics"
    }
}

