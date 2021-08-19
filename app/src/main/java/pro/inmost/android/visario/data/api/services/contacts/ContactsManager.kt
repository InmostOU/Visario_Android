package pro.inmost.android.visario.data.api.services.contacts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.contacts.AddDeleteContactRequest
import pro.inmost.android.visario.data.api.dto.requests.contacts.EditContactRequest
import pro.inmost.android.visario.data.api.utils.logError

class ContactsManager(private val service: ContactsService) {

    suspend fun getContacts() = withContext(Dispatchers.IO){
        kotlin.runCatching {
            service.getContacts().getResult()
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun addContact(username: String) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            service.addContact(AddDeleteContactRequest(username)).getResult()
        }.getOrElse  {
            logError("addContact error: " + it.message)
            Result.failure(it)
        }
    }

    suspend fun editContact(request: EditContactRequest) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            service.editContact(request).getResult()
        }.getOrElse  {
            logError("editContact error: " + it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun deleteContact(username: String) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            service.deleteContact(AddDeleteContactRequest(username)).getResult()
        }.getOrElse  {
            logError("deleteContact error: " + it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun search(username: String) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            service.search(username).getResult()
        }.getOrElse {
            logError("searchContacts error: " + it.message ?: "")
            Result.failure(it)
        }
    }
}