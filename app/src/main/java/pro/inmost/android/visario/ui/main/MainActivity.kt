package pro.inmost.android.visario.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pro.inmost.android.visario.R
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.databinding.ActivityMainBinding
import pro.inmost.android.visario.domain.usecases.auth.UpdateTokensUseCase
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.utils.*

val Context.dataStore by preferencesDataStore(AUTH_DATA_STORE)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val splashTime = 2000L
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }
    private val credentials: CredentialsStore by inject()
    private val updateTokensUseCase: UpdateTokensUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setupWithNavController(navController)

        lifecycleScope.launch {
            delay(splashTime)
            observeAuth()
        }
    }

    private suspend fun observeAuth() {
        credentials.data.collect { pref ->
            val accessToken = pref[stringPreferencesKey(PREF_KEY_ACCESS_TOKEN)]
            val refreshToken = pref[stringPreferencesKey(PREF_KEY_REFRESH_TOKEN)]
            val user = pref[stringPreferencesKey(PREF_KEY_USER)]

            if (accessToken.isNullOrBlank()
                || refreshToken.isNullOrBlank()
                || user.isNullOrBlank()
            ) {
                openLoginScreen()
            } else {
                AppDatabase.init(this, user)
                updateTokens(accessToken, refreshToken)
                openApp()
            }
            delay(500)
            hideSplash()
        }
    }

    private fun updateTokens(accessToken: String, refreshToken: String) {
        updateTokensUseCase.update(
            Tokens(accessToken, refreshToken)
        )
    }

    fun setBottomNavVisibility(visibility: Int) {
        binding.navView.visibility = visibility
    }

    private fun openApp() {
        setNavGraph(R.navigation.app_navigation)
    }

    private fun openLoginScreen() {
        setNavGraph(R.navigation.login_navigation)
    }

    private fun hideSplash() {
        binding.splashScreen.root.gone()
    }

    private fun setNavGraph(navGraphId: Int) {
        navController.setGraph(navGraphId)
    }
}