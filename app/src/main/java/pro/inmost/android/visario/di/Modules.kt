package pro.inmost.android.visario.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.repositories.*
import pro.inmost.android.visario.domain.usecases.AccountUseCase
import pro.inmost.android.visario.domain.usecases.ChannelsUseCase
import pro.inmost.android.visario.domain.usecases.ContactsUseCase
import pro.inmost.android.visario.domain.usecases.MessagingUseCase
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.calls.CallsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.chats.messages.MessagesViewModel
import pro.inmost.android.visario.ui.screens.contacts.ContactsViewModel
import pro.inmost.android.visario.ui.screens.settings.SettingsViewModel

val appModule = module {
    single { ChimeApi() }
    single { AppDatabase.getInstance(androidContext()) }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel(get()) }
    viewModel { MessagesViewModel(get()) }
    viewModel { CallsViewModel() }
    viewModel { ContactsViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

val repositories = module {
    single { AccountRepositoryImpl(get()) }
    single { ChannelsNetworkRepositoryImpl(get()) }
    single { ChannelsLocalRepositoryImpl(get()) }
    single { MessagesNetworkRepositoryImpl(get()) }
    single { MessagesLocalRepositoryImpl(get()) }
    single { ContactsNetworkRepositoryImpl(get()) }
}

val dao = module {
    single { (get() as AppDatabase).channelsDao() }
    single { (get() as AppDatabase).messagesDao() }
}

val useCases = module {
    single {
        ChannelsUseCase(
            localRepository = get<ChannelsLocalRepositoryImpl>(),
            networkRepository = get<ChannelsNetworkRepositoryImpl>()
        )
    }
    single {
        MessagingUseCase(
            localRepository = get<MessagesLocalRepositoryImpl>(),
            networkRepository = get<MessagesNetworkRepositoryImpl>()
        )
    }
    single {
        ContactsUseCase(
            networkRepository = get<ContactsNetworkRepositoryImpl>()
        )
    }
    single {
        AccountUseCase(
            repository = get<AccountRepositoryImpl>()
        )
    }
}