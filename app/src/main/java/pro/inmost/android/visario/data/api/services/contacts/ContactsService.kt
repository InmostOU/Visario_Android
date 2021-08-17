package pro.inmost.android.visario.data.api.services.contacts

import pro.inmost.android.visario.data.api.dto.requests.ContactRequest
import pro.inmost.android.visario.data.api.dto.requests.EditContactRequest
import pro.inmost.android.visario.data.api.dto.responses.StandardDataResponse
import pro.inmost.android.visario.data.api.dto.responses.StandardResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.contact.ContactData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContactsService {

    @POST(Endpoints.CONTACTS_GET)
    suspend fun getContacts(): StandardDataResponse<ContactData>

    @POST(Endpoints.CONTACT_ADD)
    suspend fun addContact(@Body request: ContactRequest): StandardResponse

    @GET(Endpoints.CONTACT_FIND)
    suspend fun search(@Query("username") username: String): StandardDataResponse<ContactData>

    @POST(Endpoints.CONTACT_DELETE)
    suspend fun deleteContact(@Body request: ContactRequest): StandardResponse

    @POST(Endpoints.CONTACT_EDIT)
    suspend fun editContact(@Body request: EditContactRequest): StandardResponse
}