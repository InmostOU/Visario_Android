package pro.inmost.android.visario.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pro.inmost.android.visario.R
import pro.inmost.android.visario.VisarioApp
import pro.inmost.android.visario.data.api.services.websockets.channels.ChannelsWebSocketClient
import pro.inmost.android.visario.databinding.ActivityMainBinding
import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.ui.screens.auth.AppPreferences
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.utils.extensions.gone
import pro.inmost.android.visario.utils.extensions.toast

class MainActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityMainBinding
    private val splashTime = 2000L
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }
    private val mAppPreferences: AppPreferences by inject()
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
        if (mAppPreferences.isCredentialsNotEmpty()){
            if (mAppPreferences.requestBiometrics){
                requestBiometrics()
            } else {
                onLogin(mAppPreferences.getCredentials())
            }
        } else {
            openLoginScreen()
        }
    }

    private fun requestBiometrics() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    toast(getString(R.string.auth_error, errString))
                    openLoginScreen()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    toast(R.string.auth_succeeded)
                    onLogin(mAppPreferences.getCredentials())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    toast(R.string.auth_failed)
                    openLoginScreen()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_login))
            .setNegativeButtonText(getString(R.string.use_password))
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    fun setBottomNavVisibility(visibility: Int) {
        binding.navView.visibility = visibility
    }

    private fun hideSplash() {
        binding.splashScreen.root.gone()
    }

    override fun onLogin(credentials: Credentials) {
        mAppPreferences.saveCredentials(credentials)
        VisarioApp.instance?.reloadModules()
        openApp()
    }

    override fun onLogout() {
        mAppPreferences.clear()
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