package pro.inmost.android.visario.ui.screens.auth


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
    fun onLogin()

    /**
     * Called when user logout
     *
     */
    fun onLogout()
}