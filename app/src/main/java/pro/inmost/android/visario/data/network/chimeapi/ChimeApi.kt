package pro.inmost.android.visario.data.network.chimeapi

import pro.inmost.android.visario.data.network.chimeapi.auth.Authenticator
import pro.inmost.android.visario.data.network.chimeapi.auth.Authenticator.TokensHolder
import pro.inmost.android.visario.data.network.chimeapi.channels.ChannelManager
import pro.inmost.android.visario.data.network.chimeapi.messages.MessagesManager
import pro.inmost.android.visario.data.network.chimeapi.session.SessionEndpoint
import pro.inmost.android.visario.data.network.chimeapi.session.SessionManager
import pro.inmost.android.visario.data.network.chimeapi.services.*


class ChimeApi {
    private val serviceFactory =  ServiceFactory()
    val auth get() = Authenticator(
        serviceFactory.getService(AccountService::class.java, SERVER_BASE_URL)
    )
    val channels get() = ChannelManager(
        serviceFactory.getService(ChannelsService::class.java, TokensHolder.accessToken, SERVER_BASE_URL)
    )
    val messages get() = MessagesManager(
        serviceFactory.getService(MessagingService::class.java, TokensHolder.accessToken, SERVER_BASE_URL)
    )

    fun getSessionManager(endpoint: SessionEndpoint): SessionManager {
        return SessionManager(
            serviceFactory.getService(SessionService::class.java, endpoint.url)
        )
    }

    companion object{
        internal const val SERVER_BASE_URL = "http://3.129.6.178:8081/"
        const val STATUS_OK = 200
    }
}

