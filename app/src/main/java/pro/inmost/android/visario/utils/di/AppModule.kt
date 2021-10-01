package pro.inmost.android.visario.utils.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.services.websockets.channels.ChannelsWebSocketClient
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.domain.usecases.auth.impl.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore


/**
 * Main module for Koin dependency framework
 */
val appModule = module {
    single { AppDatabase.getInstance(androidApplication().applicationContext) }
    single { ChimeApi() }
    single { CredentialsStore(androidApplication().applicationContext, get<UpdateCredentialsUseCaseImpl>()) }
    factory { ChannelsWebSocketClient(get(), get(), get(), get()) }
}