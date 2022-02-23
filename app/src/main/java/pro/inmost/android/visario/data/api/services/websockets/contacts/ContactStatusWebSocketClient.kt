package pro.inmost.android.visario.data.api.services.websockets.contacts

import com.google.gson.Gson
import pro.inmost.android.visario.data.api.dto.responses.contacts.ContactStatusResponse
import pro.inmost.android.visario.data.api.services.Endpoints.PORT_SOCKET_STATUS
import pro.inmost.android.visario.data.api.services.Endpoints.SERVER_BASE_URL
import pro.inmost.android.visario.data.api.services.Endpoints.WS_CONTACTS_STATUS
import pro.inmost.android.visario.data.api.services.websockets.BaseWebSocketClient
import pro.inmost.android.visario.data.database.dao.ContactsDao
import pro.inmost.android.visario.utils.extensions.launchIO

class ContactStatusWebSocketClient(
    private val contactsDao: ContactsDao
) : BaseWebSocketClient() {
    private val gson = Gson()

    override val logTag: String = "StatusWebSocket"

    override suspend fun getWebSocketLink(): String {
        return SERVER_BASE_URL + PORT_SOCKET_STATUS + WS_CONTACTS_STATUS
    }

    override fun onMessageReceived(text: String) {
        kotlin.runCatching {
            launchIO {
                val response = gson.fromJson(text, ContactStatusResponse::class.java)
                contactsDao.getByArn(response.userArn)?.let { contact ->
                    contact.online = response.status.equals("online", true)
                    contact.lastSeen = response.lastSeen
                    contactsDao.update(contact)
                }
            }
        }
    }

}