package pro.inmost.android.visario.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.data.database.AppDatabase
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.repositories.*
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.credentials.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.login.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.register.RegistrationUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.UpdateChannelUseCaseImpl
import pro.inmost.android.visario.domain.usecases.contacts.*
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCaseImpl
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCaseImpl
import pro.inmost.android.visario.ui.screens.auth.CredentialsStore
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.calls.CallsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.chats.messages.MessagesViewModel
import pro.inmost.android.visario.ui.screens.contacts.ContactsViewModel
import pro.inmost.android.visario.ui.screens.contacts.detail.ContactDetailViewModel
import pro.inmost.android.visario.ui.screens.contacts.edit.EditContactViewModel
import pro.inmost.android.visario.ui.screens.contacts.search.ContactsSearchViewModel
import pro.inmost.android.visario.ui.screens.account.AccountViewModel
import pro.inmost.android.visario.ui.screens.account.profile.EditProfileViewModel
import pro.inmost.android.visario.ui.screens.account.security.SecurityViewModel


val appModule = module {
    factory { AppDatabase.getInstance(androidApplication().applicationContext) }
    single { ChimeApi() }
    single { CredentialsStore(androidApplication().applicationContext, get<UpdateCredentialsUseCaseImpl>()) }
}

val viewModelsModule = module {
    viewModel { ChatsViewModel(get<FetchChannelsUseCaseImpl>(), get<UpdateChannelUseCaseImpl>()) }
    viewModel { MessagesViewModel(get<FetchMessagesUseCaseImpl>(), get<SendMessageUseCaseImpl>()) }
    viewModel { CallsViewModel() }

    viewModel { ContactsViewModel(get<FetchContactsUseCaseImpl>(), get<SearchContactsUseCaseImpl>()) }
    viewModel { ContactDetailViewModel(get<FetchContactsUseCaseImpl>(), get<DeleteContactUseCaseImpl>()) }
    viewModel { ContactsSearchViewModel(get<SearchContactsUseCaseImpl>()) }
    viewModel { EditContactViewModel(get<UpdateContactUseCaseImpl>(), get<AddContactUseCaseImpl>()) }

    viewModel { LoginViewModel(get<LoginUseCaseImpl>(), get()) }
    viewModel { RegisterViewModel(get<RegistrationUseCaseImpl>()) }
    viewModel { AccountViewModel(get<LogoutUseCaseImpl>(), get(), get<FetchProfileUseCaseImpl>()) }
    viewModel { EditProfileViewModel(get<UpdateProfileUseCaseImpl>(), get<FetchProfileUseCaseImpl>()) }
    viewModel { SecurityViewModel() }
}

val repositories = module {
    factory { AccountRepositoryImpl(get()) }
    factory { ChannelsRepositoryImpl(get(), get(), get<MessagesRepositoryImpl>()) }
    factory { MessagesRepositoryImpl(get(), get()) }
    factory { ContactsRepositoryImpl(get(), get()) }
    factory { ProfileRepositoryImpl(get(), get()) }
}

val dao = module {
    factory { (get() as AppDatabase).channelsDao() }
    factory { (get() as AppDatabase).messagesDao() }
    factory { (get() as AppDatabase).contactsDao() }
    factory { (get() as AppDatabase).profileDao() }
}

val contactsUseCases = module {
    factory {
        FetchContactsUseCaseImpl(
            repository = get<ContactsRepositoryImpl>()
        )
    }
    factory {
        AddContactUseCaseImpl(
            repository = get<ContactsRepositoryImpl>()
        )
    }

    factory {
        DeleteContactUseCaseImpl(
            repository = get<ContactsRepositoryImpl>()
        )
    }
    factory {
        UpdateContactUseCaseImpl(
            repository = get<ContactsRepositoryImpl>()
        )
    }
    factory {
        SearchContactsUseCaseImpl(
            repository = get<ContactsRepositoryImpl>()
        )
    }
}

val channelsUseCases = module {
    factory {
        FetchChannelsUseCaseImpl(
            repository = get<ChannelsRepositoryImpl>()
        )
    }

    factory {
        UpdateChannelUseCaseImpl(
            repository = get<ChannelsRepositoryImpl>()
        )
    }
}

val accountUseCases = module {
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

val messagesUseCases = module {
    factory {
        SendMessageUseCaseImpl(
            repository = get<MessagesRepositoryImpl>()
        )
    }
    factory {
        FetchMessagesUseCaseImpl(
            repository = get<MessagesRepositoryImpl>()
        )
    }
}

val profileUseCases = module {
    factory {
        UpdateProfileUseCaseImpl(get<ProfileRepositoryImpl>())
    }
    factory {
        FetchProfileUseCaseImpl(get<ProfileRepositoryImpl>())
    }
}

val allModules = listOf(
    appModule,
    viewModelsModule,
    repositories,
    dao,
    contactsUseCases,
    channelsUseCases,
    accountUseCases,
    messagesUseCases,
    profileUseCases
)