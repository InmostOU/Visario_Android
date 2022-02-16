package pro.inmost.android.visario.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.facebook.AccessToken
import com.facebook.LoginStatusCallback
import com.facebook.login.LoginManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pro.inmost.android.visario.R
import pro.inmost.android.visario.VisarioApp
import pro.inmost.android.visario.data.api.services.websockets.channels.ChannelsWebSocketClient
import pro.inmost.android.visario.databinding.ActivityMainBinding
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.utils.extensions.gone
import pro.inmost.android.visario.utils.log


class MainActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityMainBinding
    private val splashTime = 2000L
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }
    private val credentialsStore: CredentialsStore by inject()
    private val channelsWebSocketClient: ChannelsWebSocketClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setupWithNavController(navController)

        lifecycleScope.launch {
            delay(splashTime)
            checkAuth()
            hideSplash()
        }
    }

    private fun checkAuth() {
        LoginManager.getInstance().retrieveLoginStatus(this, object : LoginStatusCallback {
            override fun onCompleted(accessToken: AccessToken) {
                credentialsStore.saveAccessToken(accessToken.token)
                log("on MainActivity token received: ${accessToken.token}")
            }

            override fun onFailure() {
                // No access token could be retrieved for the user
            }

            override fun onError(exception: Exception) {
                // An error occurred
            }
        })

        if (credentialsStore.isCredentialsNotEmpty()) {
            onLogin(credentialsStore.getCredentials())
        } else {
            openLoginScreen()
        }
    }

    fun setBottomNavVisibility(visibility: Int) {
        binding.navView.visibility = visibility
    }

    private fun hideSplash() {
        binding.splashScreen.root.gone()
    }

    override fun onLogin(credentials: Credentials) {
        credentialsStore.saveCredentials(credentials)
        VisarioApp.instance?.reloadModules()
        openApp()
    }

    override fun onLogout() {
        credentialsStore.clear()
        LoginManager.getInstance().logOut()
        channelsWebSocketClient.disconnect()
        openLoginScreen()
    }

    private fun openApp() {
        setNavGraph(R.navigation.app_navigation)
        lifecycleScope.launch {
            channelsWebSocketClient.connect()
        }
    }

    private fun openLoginScreen() {
        setNavGraph(R.navigation.login_navigation)
    }

    private fun setNavGraph(navGraphId: Int) {
        navController.setGraph(navGraphId)
    }
}