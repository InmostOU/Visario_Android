package pro.inmost.android.visario.utils.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.domain.usecases.auth.impl.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.impl.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.impl.RegistrationUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.impl.*
import pro.inmost.android.visario.domain.usecases.contacts.impl.*
import pro.inmost.android.visario.domain.usecases.meetings.impl.*
import pro.inmost.android.visario.domain.usecases.messages.impl.*
import pro.inmost.android.visario.domain.usecases.profile.impl.FetchProfileUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.impl.UpdateProfileUseCaseImpl
import pro.inmost.android.visario.ui.dialogs.inviter.channel.ChannelInviterViewModel
import pro.inmost.android.visario.ui.dialogs.inviter.meeting.MeetingChannelsInviterViewModel
import pro.inmost.android.visario.ui.dialogs.select.media.ImageSelectorViewModel
import pro.inmost.android.visario.ui.screens.account.account.AccountViewModel
import pro.inmost.android.visario.ui.screens.account.edit.EditProfileViewModel
import pro.inmost.android.visario.ui.screens.account.settings.privacy.birthdate.BirthdateSetupViewModel
import pro.inmost.android.visario.ui.screens.account.settings.privacy.email.EmailSetupViewModel
import pro.inmost.android.visario.ui.screens.account.settings.privacy.password.PasswordSetupViewModel
import pro.inmost.android.visario.ui.screens.account.settings.privacy.phonenumber.PhoneNumberSetupViewModel
import pro.inmost.android.visario.ui.screens.account.settings.security.SecurityViewModel
import pro.inmost.android.visario.ui.screens.auth.login.LoginViewModel
import pro.inmost.android.visario.ui.screens.auth.register.RegisterViewModel
import pro.inmost.android.visario.ui.screens.channels.create.CreateChannelViewModel
import pro.inmost.android.visario.ui.screens.channels.info.ChannelInfoViewModel
import pro.inmost.android.visario.ui.screens.channels.info.members.ChannelMembersViewModel
import pro.inmost.android.visario.ui.screens.channels.list.ChannelsViewModel
import pro.inmost.android.visario.ui.screens.channels.messages.MessagesViewModel
import pro.inmost.android.visario.ui.screens.channels.search.SearchChannelsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.contacts.detail.ContactDetailViewModel
import pro.inmost.android.visario.ui.screens.contacts.edit.EditContactViewModel
import pro.inmost.android.visario.ui.screens.contacts.list.ContactsViewModel
import pro.inmost.android.visario.ui.screens.contacts.search.ContactsSearchViewModel
import pro.inmost.android.visario.ui.screens.meet.join.JoinMeetingViewModel
import pro.inmost.android.visario.ui.screens.meet.list.ScheduledMeetingsViewModel
import pro.inmost.android.visario.ui.screens.meet.meeting.MeetingViewModel

/**
 * ViewModels module for Koin dependency framework
 */
val viewModelsModule = module {
    // CHANNELS
    viewModel { ChannelsViewModel(get<FetchChannelsUseCaseImpl>(), get<UpdateProfileUseCaseImpl>()) }
    viewModel { SearchChannelsViewModel(get<FetchChannelsUseCaseImpl>()) }
    viewModel { CreateChannelViewModel(get<CreateChannelUseCaseImpl>(), get<FetchChannelsUseCaseImpl>()) }
    viewModel { ChannelInfoViewModel(get<FetchChannelsUseCaseImpl>()) }
    viewModel { ChannelMembersViewModel(get<FetchChannelMembersUseCaseImpl>()) }
    viewModel { ChannelInviterViewModel(
        get<FetchContactsUseCaseImpl>(),
        get<FetchChannelMembersUseCaseImpl>(),
        get<AddMemberToChannelUseCaseImpl>()
    ) }

    // MESSAGES
    viewModel {
        MessagesViewModel(
            get<FetchMessagesUseCaseImpl>(),
            get<FetchChannelsUseCaseImpl>(),
            get<FetchProfileUseCaseImpl>(),
            get<SendMessageUseCaseImpl>(),
            get<EditMessageUseCaseImpl>(),
            get<DeleteMessageUseCaseImpl>(),
            get<LeaveChannelUseCaseImpl>(),
            get<UpdateMessagesReadStatusUseCaseImpl>(),
            get<AddMemberToChannelUseCaseImpl>(),
            get<CreateMeetingUseCaseImpl>(),
            get<SendInvitationUseCaseImpl>()
        )
    }

    // MEETINGS
    viewModel { ScheduledMeetingsViewModel() }
    viewModel { JoinMeetingViewModel() }
    viewModel { MeetingChannelsInviterViewModel(
        get<FetchChannelsUseCaseImpl>(),
        get<SendInvitationUseCaseImpl>())
    }
    viewModel { MeetingViewModel(
        get<CreateMeetingUseCaseImpl>(),
        get<JoinMeetingUseCaseImpl>(),
        get<DeleteAttendeeUseCaseImpl>(),
        get<GetAttendeeUseCaseImpl>(),
        get<FetchProfileUseCaseImpl>())
    }

    // CHATS
    viewModel { ChatsViewModel() }

    // CONTACTS
    viewModel {
        ContactsViewModel(
            get<FetchContactsUseCaseImpl>(),
            get<SearchContactsUseCaseImpl>()
        )
    }
    viewModel {
        ContactDetailViewModel(
            get<FetchContactsUseCaseImpl>(),
            get<DeleteContactUseCaseImpl>()
        )
    }
    viewModel { ContactsSearchViewModel(get<SearchContactsUseCaseImpl>()) }
    viewModel {
        EditContactViewModel(
            get<UpdateContactUseCaseImpl>(),
            get<AddContactUseCaseImpl>()
        )
    }

    // ACCOUNT
    viewModel { LoginViewModel(get<LoginUseCaseImpl>()) }
    viewModel { RegisterViewModel(get<RegistrationUseCaseImpl>()) }
    viewModel {
        AccountViewModel(
            get<LogoutUseCaseImpl>(),
            get<FetchProfileUseCaseImpl>(),
            get<UpdateProfileUseCaseImpl>()
        )
    }
    viewModel {
        EditProfileViewModel(
            get<UpdateProfileUseCaseImpl>(),
            get<FetchProfileUseCaseImpl>()
        )
    }

    // SETTINGS
    viewModel { SecurityViewModel(get()) }

    viewModel {
        PhoneNumberSetupViewModel(
            get<FetchProfileUseCaseImpl>(),
            get<UpdateProfileUseCaseImpl>()
        )
    }
    viewModel {
        EmailSetupViewModel(
            get<FetchProfileUseCaseImpl>(),
            get<UpdateProfileUseCaseImpl>()
        )
    }
    viewModel {
        BirthdateSetupViewModel(
            get<FetchProfileUseCaseImpl>(),
            get<UpdateProfileUseCaseImpl>()
        )
    }
    viewModel {
        PasswordSetupViewModel(
            get<FetchProfileUseCaseImpl>(),
            get<UpdateProfileUseCaseImpl>()
        )
    }
    viewModel { ImageSelectorViewModel() }


}