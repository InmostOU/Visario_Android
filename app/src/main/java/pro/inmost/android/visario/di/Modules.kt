package pro.inmost.android.visario.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.repositories.*
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.UpdateTokensUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.login.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.register.RegistrationUseCaseImpl
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.calls.CallsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.chats.messages.MessagesViewModel
import pro.inmost.android.visario.ui.screens.contacts.ContactsViewModel
import pro.inmost.android.visario.ui.screens.settings.SettingsViewModel

val appModule = module {
    factory { AppDatabase.instance }
    single { ChimeApi() }
    single { CredentialsStore(androidApplication().applicationContext) }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel(get(), get()) }
    viewModel { MessagesViewModel(get(), get(), get()) }
    viewModel { CallsViewModel() }
    viewModel { ContactsViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

val repositories = module {
    factory { AccountRepositoryImpl(get()) }
    factory { ChannelsNetworkRepositoryImpl(get()) }
    factory { ChannelsLocalRepositoryImpl(get(), get()) }
    factory { ContactsNetworkRepositoryImpl(get()) }
}

val dao = module {
    factory { (get() as AppDatabase).channelsDao() }
    factory { (get() as AppDatabase).messagesDao() }
}

val useCases = module {
    factory {
        FetchChannelsUseCaseImpl(
            localRepository = get<ChannelsLocalRepositoryImpl>(),
            networkRepository = get<ChannelsNetworkRepositoryImpl>(),
        )
    }

    factory {
        UpdateTokensUseCaseImpl(
            repository = get<AccountRepositoryImpl>()
        )
    }
    factory {
        LoginUseCaseImpl(
            repository = get<AccountRepositoryImpl>()
        )
    }
    factory {
        RegistrationUseCaseImpl(
            repository = get<AccountRepositoryImpl>()
        )
    }
    factory {
        LogoutUseCaseImpl(
            repository = get<AccountRepositoryImpl>()
        )
    }
}