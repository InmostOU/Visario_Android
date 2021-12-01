package pro.inmost.android.visario.ui.screens.auth

import pro.inmost.android.visario.domain.entities.user.Credentials


/**
 * User authentication listener. Base activity should be implemented this interface
 * to change change screens when user login or logout
 *
 */
interface AuthListener {
    /**
     * Called when user login
     *
     */
    fun onLogin(credentials: Credentials)

    /**
     * Called when user logout
     *
     */
    fun onLogout()
}