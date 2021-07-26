package pro.inmost.android.visario.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.core.data.network.AuthenticatorImpl
import pro.inmost.android.visario.core.data.network.TokensHolder
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.calls.CallsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.contacts.ContactsViewModel
import pro.inmost.android.visario.ui.screens.notifications.NotificationsViewModel
import pro.inmost.android.visario.ui.screens.settings.SettingsViewModel

val appModule = module {
    single { AuthenticatorImpl() }
    single { TokensHolder }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel() }
    viewModel { LoginViewModel(get<AuthenticatorImpl>()) }
    viewModel { RegisterViewModel(get<AuthenticatorImpl>()) }
    viewModel { SettingsViewModel(get<AuthenticatorImpl>()) }
    viewModel { CallsViewModel() }
    viewModel { ContactsViewModel() }
    viewModel { NotificationsViewModel() }
}