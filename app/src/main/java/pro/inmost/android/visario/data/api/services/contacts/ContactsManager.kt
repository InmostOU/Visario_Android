package pro.inmost.android.visario.data.api.services.contacts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.requests.ContactRequest
import pro.inmost.android.visario.data.api.dto.requests.EditContactRequest
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.api.utils.logInfo

class ContactsManager(private val service: ContactsService) {

    suspend fun getContacts() = withContext(Dispatchers.IO){
        kotlin.runCatching {
            val response = service.getContacts()
            logInfo("getContacts: $response")
            if (response.status == ChimeApi.STATUS_OK){
                Result.success(response.data)
            } else {
                val message = "${response.status}: ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse {
            logError(it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun addContact(username: String) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            val response = service.addContact(ContactRequest(username))
            if (response.status == ChimeApi.STATUS_OK){
                Result.success(Unit)
            } else {
                val message = "${response.status}: ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse  {
            logError("addContact error: " + it.message)
            Result.failure(it)
        }
    }

    suspend fun editContact(request: EditContactRequest) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            val response = service.editContact(request)
            if (response.status == ChimeApi.STATUS_OK){
                Result.success(Unit)
            } else {
                val message = "editContact error: ${response.status} ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse  {
            logError("editContact error: " + it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun deleteContact(username: String) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            val response = service.deleteContact(ContactRequest(username))
            if (response.status == ChimeApi.STATUS_OK){
                Result.success(Unit)
            } else {
                val message = "deleteContact error: ${response.status} ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse  {
            logError("deleteContact error: " + it.message ?: "")
            Result.failure(it)
        }
    }

    suspend fun search(username: String) = withContext(Dispatchers.IO){
        kotlin.runCatching {
            val response = service.search(username)
            logInfo("searchContact response: $response")
            if (response.status == ChimeApi.STATUS_OK){
                Result.success(response.data)
            } else {
                val message = "searchContacts error: ${response.status} ${response.message}"
                logError(message)
                Result.failure(Throwable(message))
            }
        }.getOrElse {
            logError("searchContacts error: " + it.message ?: "")
            Result.failure(it)
        }
    }
}