package pro.inmost.android.visario.di.modules

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.domain.usecases.auth.credentials.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore

val appModule = module {
    factory { AppDatabase.getInstance(androidApplication().applicationContext) }
    single { ChimeApi() }
    single { CredentialsStore(androidApplication().applicationContext, get<UpdateCredentialsUseCaseImpl>()) }
}