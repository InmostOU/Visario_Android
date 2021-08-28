package pro.inmost.android.visario.di.modules

import org.koin.dsl.module
import pro.inmost.android.visario.data.repositories.*
import pro.inmost.android.visario.domain.usecases.auth.credentials.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.login.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.register.RegistrationUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.*
import pro.inmost.android.visario.domain.usecases.contacts.*
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCaseImpl
import pro.inmost.android.visario.domain.usecases.meetings.JoinMeetingUseCaseImpl
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCaseImpl
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCaseImpl

val useCases = module {

    // CONTACTS
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


    // CHANNELS
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
    factory {
        CreateChannelUseCaseImpl(
            repository = get<ChannelsRepositoryImpl>()
        )
    }
    factory {
        LeaveChannelUseCaseImpl(
            repository = get<ChannelsRepositoryImpl>()
        )
    }

    factory {
        AddMemberToChannelUseCaseImpl(
            repository = get<ChannelsRepositoryImpl>()
        )
    }

    // MEETINGS
    factory {
        CreateMeetingUseCaseImpl(
            repository = get<MeetingsRepositoryImpl>()
        )
    }
    factory {
        JoinMeetingUseCaseImpl(
            repository = get<MeetingsRepositoryImpl>()
        )
    }


    // ACCOUNT
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


    // MESSAGES
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


    // PROFILE
    factory {
        UpdateProfileUseCaseImpl(get<ProfileRepositoryImpl>())
    }
    factory {
        FetchProfileUseCaseImpl(get<ProfileRepositoryImpl>())
    }
}