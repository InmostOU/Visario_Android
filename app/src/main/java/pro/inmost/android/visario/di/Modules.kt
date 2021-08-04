package pro.inmost.android.visario.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi
import pro.inmost.android.visario.data.repositories.*
import pro.inmost.android.visario.domain.usecases.channels.ObserveChannelsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.credentials.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.login.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.register.RegistrationUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.SaveChannelsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCaseImpl
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.calls.CallsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.chats.messages.MessagesViewModel
import pro.inmost.android.visario.ui.screens.contacts.ContactsViewModel
import pro.inmost.android.visario.ui.screens.settings.SettingsViewModel

val appModule = module {
    factory { AppDatabase.getInstance(androidApplication().applicationContext) }
    single { ChimeApi() }
    single { CredentialsStore(androidApplication().applicationContext, get<UpdateCredentialsUseCaseImpl>()) }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel(get<ObserveChannelsUseCaseImpl>(), get<SaveChannelsUseCaseImpl>()) }
    viewModel { MessagesViewModel(get<ObserveChannelsUseCaseImpl>(), get<SendMessageUseCaseImpl>(), get<SaveChannelsUseCaseImpl>()) }
    viewModel { CallsViewModel() }
    viewModel { ContactsViewModel() }
    viewModel { LoginViewModel(get<LoginUseCaseImpl>(), get()) }
    viewModel { RegisterViewModel(get<RegistrationUseCaseImpl>()) }
    viewModel { SettingsViewModel(get<LogoutUseCaseImpl>(), get()) }
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
        ObserveChannelsUseCaseImpl(
            localRepository = get<ChannelsLocalRepositoryImpl>(),
            networkRepository = get<ChannelsNetworkRepositoryImpl>(),
        )
    }

    factory {
        SaveChannelsUseCaseImpl(
            repository = get<ChannelsLocalRepositoryImpl>()
        )
    }
    factory {
        SendMessageUseCaseImpl(
            repository = get<ChannelsNetworkRepositoryImpl>()
        )
    }

    factory {
        UpdateCredentialsUseCaseImpl(
            repository = get<AccountRepositoryImpl>()
        )
    }
    factory {
        LoginUseCaseImpl (
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