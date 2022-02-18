package pro.inmost.android.visario.utils.di

import android.content.Context
import android.content.Context
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pro.inmost.android.visario.R
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.services.websockets.channels.ChannelsWebSocketClient
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.domain.usecases.auth.impl.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.ui.screens.auth.AppPreferences


/**
 * Main module for Koin dependency framework
 */
val appModule = module {
    single { AppDatabase.getInstance(androidApplication().applicationContext) }
    single { ChimeApi() }
    single {
        AppPreferences(
            androidApplication()
                .applicationContext
                .getSharedPreferences("app_pref", Context.MODE_PRIVATE),
            get<UpdateCredentialsUseCaseImpl>()
        )
    }
    factory { ChannelsWebSocketClient(get(), get(), get()) }
    single { getGoogleSignInClient(androidApplication().applicationContext) }
    single { LoginManager.getInstance() }
}

private fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.server_client_id))
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}