package pro.inmost.android.visario.data.network.chimeapi

import pro.inmost.android.visario.data.network.chimeapi.auth.Authenticator
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.data.network.chimeapi.channels.ChannelManager
import pro.inmost.android.visario.data.network.chimeapi.messages.MessagesManager
import pro.inmost.android.visario.data.network.chimeapi.services.AccountService
import pro.inmost.android.visario.data.network.chimeapi.services.ChannelsService
import pro.inmost.android.visario.data.network.chimeapi.services.MessagesService
import pro.inmost.android.visario.data.network.chimeapi.services.ServiceFactory


class ChimeApi {
    private val serviceFactory =  ServiceFactory()
    val auth get() = Authenticator(
        serviceFactory.getService(AccountService::class.java)
    )
    val channels get() = ChannelManager(
        serviceFactory.getService(ChannelsService::class.java, auth.accessToken)
    )
    val messages get() = MessagesManager(
        serviceFactory.getService(MessagesService::class.java, auth.accessToken)
    )

    companion object{
        internal const val SERVER_BASE_URL = "http://3.129.6.178:8081/"
        const val STATUS_OK = 200
    }
}

