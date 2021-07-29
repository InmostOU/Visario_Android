package pro.inmost.android.visario.ui.main

import android.content.Context
import android.os.Bundle
import android.view.MenuInflater
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
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.databinding.ActivityMainBinding
import pro.inmost.android.visario.ui.utils.*

val Context.dataStore by preferencesDataStore(AUTH_DATA_STORE)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val splashTime = 2000L
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setupWithNavController(navController)
        lifecycleScope.launch {
            delay(splashTime)
            checkAuth()
        }
    }

    private suspend fun checkAuth() {
        dataStore.data.collect { pref ->
            val accessToken = pref[stringPreferencesKey(PREF_KEY_ACCESS_TOKEN)]
            val refreshToken = pref[stringPreferencesKey(PREF_KEY_REFRESH_TOKEN)]
            if (accessToken.isNullOrBlank() || refreshToken.isNullOrBlank()) {
                openLoginScreen()
            } else {
                setTokens(accessToken, refreshToken)
                openApp()
            }
            delay(500)
            hideSplash()
        }
    }

    private fun setTokens(accessToken: String, refreshToken: String) {
        ChimeApi.TokensHolder.updateTokens(
            Tokens(accessToken, refreshToken)
        )
    }

    fun setBottomNavVisibility(visibility: Int) {
        binding.navView.visibility = visibility
    }

    private fun openApp() {
        setGraph(R.navigation.app_navigation)
    }

    private fun openLoginScreen() {
        setGraph(R.navigation.login_navigation)
    }

    private fun hideSplash() {
        binding.splashScreen.root.gone()
    }

    private fun setGraph(navGraphId: Int) {
        navController.setGraph(navGraphId)
    }
}