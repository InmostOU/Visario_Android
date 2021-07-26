package pro.inmost.android.visario.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.data.network.AuthenticatorImpl
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel

val appModule = module {
    single { AuthenticatorImpl() }
    single { androidContext() }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel() }
    viewModel { LoginViewModel(get<AuthenticatorImpl>()) }
    viewModel { RegisterViewModel(get<AuthenticatorImpl>()) }
}