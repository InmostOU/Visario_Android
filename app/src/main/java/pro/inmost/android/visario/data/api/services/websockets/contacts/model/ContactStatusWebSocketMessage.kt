package pro.inmost.android.visario.data.api.services.websockets.contacts.model

import pro.inmost.android.visario.data.api.dto.responses.contacts.ContactStatusResponse

data class ContactStatusWebSocketMessage(
    val table: String,
    val action: String,
    val data: List<ContactStatusResponse>
)
