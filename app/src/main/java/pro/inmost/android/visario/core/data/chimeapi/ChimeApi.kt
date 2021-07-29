package pro.inmost.android.visario.core.data.chimeapi

import pro.inmost.android.visario.core.data.chimeapi.auth.Authenticator
import pro.inmost.android.visario.core.data.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.core.data.chimeapi.channels.ChannelManager
import pro.inmost.android.visario.core.data.chimeapi.messages.MessagesManager
import pro.inmost.android.visario.core.data.chimeapi.services.AccountService
import pro.inmost.android.visario.core.data.chimeapi.services.ChannelsService
import pro.inmost.android.visario.core.data.chimeapi.services.MessagesService
import pro.inmost.android.visario.core.data.chimeapi.services.ServiceFactory


class ChimeApi {
    private val serviceFactory =  ServiceFactory()
    val auth get() = Authenticator(
        serviceFactory.getService(AccountService::class.java)
    )
    val channels get() = ChannelManager(
        serviceFactory.getService(ChannelsService::class.java, TokensHolder.accessToken)
    )
    val messages get() = MessagesManager(
        serviceFactory.getService(MessagesService::class.java, TokensHolder.accessToken)
    )


    internal object TokensHolder {
        private var tokens: Tokens? = null
        val accessToken get() = tokens?.accessToken ?: ""
        val refreshToken get() = tokens?.refreshToken ?: ""

        fun updateTokens(tokens: Tokens){
            this.tokens = tokens
        }

        fun deleteTokens(){
            tokens = null
        }

    }

    companion object{
        internal const val SERVER_BASE_URL = "http://3.129.6.178:8081/"
        const val STATUS_OK = 200
    }
}

