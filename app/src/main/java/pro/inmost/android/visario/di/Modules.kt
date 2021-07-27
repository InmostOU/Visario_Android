package pro.inmost.android.visario.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.repositories.ChatsRepositoryImpl
import pro.inmost.android.visario.core.data.repositories.AccountRepositoryImpl
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.calls.CallsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChannelListAdapter
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.contacts.ContactsViewModel
import pro.inmost.android.visario.ui.screens.notifications.NotificationsViewModel
import pro.inmost.android.visario.ui.screens.settings.SettingsViewModel

val appModule = module {
    single { ChimeApi() }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel(get<ChatsRepositoryImpl>()) }
    viewModel { LoginViewModel(get<AccountRepositoryImpl>()) }
    viewModel { RegisterViewModel(get<AccountRepositoryImpl>()) }
    viewModel { SettingsViewModel(get<AccountRepositoryImpl>()) }
    viewModel { CallsViewModel() }
    viewModel { ContactsViewModel() }
    viewModel { NotificationsViewModel() }
}

val adapters = module {
    factory { ChannelListAdapter(get()) }
}

val repositories = module {
    single { AccountRepositoryImpl(get()) }
    single { ChatsRepositoryImpl(get()) }
}