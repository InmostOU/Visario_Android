package pro.inmost.android.visario.data.api

import pro.inmost.android.visario.data.api.services.ServiceFactory
import pro.inmost.android.visario.data.api.services.account.AccountManager
import pro.inmost.android.visario.data.api.services.account.AccountService
import pro.inmost.android.visario.data.api.services.auth.AuthService
import pro.inmost.android.visario.data.api.services.auth.Authenticator
import pro.inmost.android.visario.data.api.services.auth.Authenticator.TokensHolder
import pro.inmost.android.visario.data.api.services.channels.ChannelManager
import pro.inmost.android.visario.data.api.services.channels.ChannelsService
import pro.inmost.android.visario.data.api.services.contacts.ContactsManager
import pro.inmost.android.visario.data.api.services.contacts.ContactsService
import pro.inmost.android.visario.data.api.services.messages.MessagesManager
import pro.inmost.android.visario.data.api.services.messages.MessagingService
import pro.inmost.android.visario.data.api.services.session.SessionManager
import pro.inmost.android.visario.data.api.services.session.SessionService


class ChimeApi {
    private val serviceFactory =  ServiceFactory(TokensHolder)
    val auth get() = Authenticator(
        serviceFactory.getService(AuthService::class.java)
    )
    val channels get() = ChannelManager(
        serviceFactory.getService(ChannelsService::class.java)
    )
    val messages get() = MessagesManager(
        serviceFactory.getService(MessagingService::class.java)
    )
    val contacts get() = ContactsManager(
        serviceFactory.getService(ContactsService::class.java)
    )
    val user get() = AccountManager(
        serviceFactory.getService(AccountService::class.java)
    )
    val session get() = SessionManager(
        serviceFactory.getService(SessionService::class.java)
    )

    companion object{
        const val STATUS_OK = 200
    }
}

