package pro.inmost.android.visario.data.api.services.contacts

import pro.inmost.android.visario.data.api.dto.requests.contacts.AddContactRequest
import pro.inmost.android.visario.data.api.dto.requests.contacts.DeleteContactRequest
import pro.inmost.android.visario.data.api.dto.requests.contacts.EditContactRequest
import pro.inmost.android.visario.data.api.dto.responses.base.BaseDataResponse
import pro.inmost.android.visario.data.api.dto.responses.base.BaseResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.contact.ContactData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Contacts service to be implemented by retrofit
 *
 */
interface ContactsService {

    @POST(Endpoints.CONTACTS_GET)
    suspend fun getContacts(): BaseDataResponse<List<ContactData>>

    @POST(Endpoints.CONTACT_ADD)
    suspend fun addContact(@Body request: AddContactRequest): BaseResponse

    @GET(Endpoints.CONTACT_FIND)
    suspend fun search(@Query("username") username: String): BaseDataResponse<List<ContactData>>

    @POST(Endpoints.CONTACT_DELETE)
    suspend fun deleteContact(@Body request: DeleteContactRequest): BaseResponse

    @POST(Endpoints.CONTACT_EDIT)
    suspend fun editContact(@Body request: EditContactRequest): BaseResponse
}