package pro.inmost.android.visario.di.modules

import org.koin.dsl.module
import pro.inmost.android.visario.data.repositories.*
import pro.inmost.android.visario.domain.usecases.auth.impl.UpdateCredentialsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.impl.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.impl.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.impl.RegistrationUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.impl.*
import pro.inmost.android.visario.domain.usecases.contacts.impl.*
import pro.inmost.android.visario.domain.usecases.meetings.impl.*
import pro.inmost.android.visario.domain.usecases.messages.impl.*
import pro.inmost.android.visario.domain.usecases.profile.impl.FetchProfileUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.impl.UpdateProfileUseCaseImpl

/**
 * Use Cases module for Koin dependency framework
 */
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
        GetAttendeeUseCaseImpl(
            repository = get<MeetingsRepositoryImpl>()
        )
    }
    factory {
        JoinMeetingUseCaseImpl(
            repository = get<MeetingsRepositoryImpl>()
        )
    }

    factory {
        SendInvitationUseCaseImpl(
            repository = get<MeetingsRepositoryImpl>()
        )
    }
    factory {
        DeleteAttendeeUseCaseImpl(
            meetingsRepository = get<MeetingsRepositoryImpl>(),
            profileRepository = get<ProfileRepositoryImpl>()
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

    factory {
        UpdateMessagesReadStatusUseCaseImpl(
            repository = get<MessagesRepositoryImpl>()
        )
    }
    factory {
        EditMessageUseCaseImpl(
            repository = get<MessagesRepositoryImpl>()
        )
    }
    factory {
        DeleteMessageUseCaseImpl(
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