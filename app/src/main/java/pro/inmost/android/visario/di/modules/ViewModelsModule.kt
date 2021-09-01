package pro.inmost.android.visario.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pro.inmost.android.visario.domain.usecases.auth.login.LoginUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.logout.LogoutUseCaseImpl
import pro.inmost.android.visario.domain.usecases.auth.register.RegistrationUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.CreateChannelUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCaseImpl
import pro.inmost.android.visario.domain.usecases.channels.LeaveChannelUseCaseImpl
import pro.inmost.android.visario.domain.usecases.contacts.*
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCaseImpl
import pro.inmost.android.visario.domain.usecases.meetings.JoinMeetingUseCaseImpl
import pro.inmost.android.visario.domain.usecases.messages.FetchMessagesUseCaseImpl
import pro.inmost.android.visario.domain.usecases.messages.SendMessageUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCaseImpl
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCaseImpl
import pro.inmost.android.visario.ui.dialogs.select.contacts.ContactsInviterViewModel
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
import pro.inmost.android.visario.ui.screens.channels.list.ChannelsViewModel
import pro.inmost.android.visario.ui.screens.channels.messages.MessagesViewModel
import pro.inmost.android.visario.ui.screens.channels.search.SearchChannelsViewModel
import pro.inmost.android.visario.ui.screens.chats.ChatsViewModel
import pro.inmost.android.visario.ui.screens.contacts.detail.ContactDetailViewModel
import pro.inmost.android.visario.ui.screens.contacts.edit.EditContactViewModel
import pro.inmost.android.visario.ui.screens.contacts.list.ContactsViewModel
import pro.inmost.android.visario.ui.screens.contacts.search.ContactsSearchViewModel
import pro.inmost.android.visario.ui.screens.meet.create.CreateMeetingViewModel
import pro.inmost.android.visario.ui.screens.meet.join.JoinMeetingViewModel
import pro.inmost.android.visario.ui.screens.meet.list.MeetingsViewModel
import pro.inmost.android.visario.ui.screens.meet.meeting.MeetingViewModel

val viewModelsModule = module {
    viewModel { ChannelsViewModel(get<FetchChannelsUseCaseImpl>()) }
    viewModel { SearchChannelsViewModel(get<FetchChannelsUseCaseImpl>()) }
    viewModel { CreateChannelViewModel(get<CreateChannelUseCaseImpl>()) }
    viewModel {
        MessagesViewModel(
            get<FetchMessagesUseCaseImpl>(),
            get<FetchProfileUseCaseImpl>(),
            get<SendMessageUseCaseImpl>(),
            get<LeaveChannelUseCaseImpl>()
        )
    }
    viewModel { MeetingsViewModel() }
    viewModel { JoinMeetingViewModel(get<JoinMeetingUseCaseImpl>()) }
    viewModel { CreateMeetingViewModel(get<CreateMeetingUseCaseImpl>()) }
    viewModel { MeetingViewModel() }
    viewModel { ChatsViewModel() }

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

    viewModel { LoginViewModel(get<LoginUseCaseImpl>(), get()) }
    viewModel { RegisterViewModel(get<RegistrationUseCaseImpl>()) }
    viewModel {
        AccountViewModel(
            get<LogoutUseCaseImpl>(),
            get(),
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
    viewModel { SecurityViewModel() }

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
    viewModel { ContactsInviterViewModel(
        get<FetchContactsUseCaseImpl>(),
        get<AddMemberToChannelUseCaseImpl>()
    ) }
}