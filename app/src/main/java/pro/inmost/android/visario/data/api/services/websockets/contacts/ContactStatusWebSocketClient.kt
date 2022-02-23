package pro.inmost.android.visario.data.api.services.websockets.contacts

import pro.inmost.android.visario.data.api.dto.responses.contacts.ContactStatusResponse
import pro.inmost.android.visario.data.api.services.Endpoints.PORT_SOCKET_STATUS
import pro.inmost.android.visario.data.api.services.Endpoints.SERVER_BASE_URL
import pro.inmost.android.visario.data.api.services.Endpoints.WS_CONTACTS_STATUS
import pro.inmost.android.visario.data.api.services.websockets.BaseWebSocketClient
import pro.inmost.android.visario.data.database.dao.ContactsDao
import pro.inmost.android.visario.utils.extensions.launchIO
import pro.inmost.android.visario.utils.extensions.parseJson

class ContactStatusWebSocketClient(
    private val contactsDao: ContactsDao
) : BaseWebSocketClient() {
    override val logTag: String = "StatusWebSocket"

    override suspend fun getWebSocketLink(): String {
        return SERVER_BASE_URL + PORT_SOCKET_STATUS + WS_CONTACTS_STATUS
    }

    override fun onMessageReceived(text: String) {
        launchIO {
            text.parseJson<ContactStatusResponse>()?.let { response ->
                updateContact(response)
            }
        }
    }

    private suspend fun updateContact(response: ContactStatusResponse) {
        contactsDao.getByArn(response.userArn)?.let { contact ->
            contact.online = response.status.equals("online", true)
            contact.lastSeen = response.lastSeen
            contactsDao.update(contact)
        }
    }
}