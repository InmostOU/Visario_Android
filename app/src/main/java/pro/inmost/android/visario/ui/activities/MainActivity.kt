package pro.inmost.android.visario.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pro.inmost.android.visario.R
import pro.inmost.android.visario.VisarioApp
import pro.inmost.android.visario.databinding.ActivityMainBinding
import pro.inmost.android.visario.ui.screens.auth.AuthListener
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.utils.extensions.gone

class MainActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityMainBinding
    private val splashTime = 2000L
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }
    private val credentialsStore: CredentialsStore by inject()

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
        if (credentialsStore.isCredentialsNotEmpty()){
            credentialsStore.update()
            openApp()
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

    override fun onLogin() {
        openApp()
    }

    override fun onLogout() {
        VisarioApp.instance?.reloadModules()
        openLoginScreen()
    }

    private fun openApp() {
        setNavGraph(R.navigation.app_navigation)
    }

    private fun openLoginScreen() {
        setNavGraph(R.navigation.login_navigation)
    }

    private fun setNavGraph(navGraphId: Int) {
        navController.setGraph(navGraphId)
    }
}