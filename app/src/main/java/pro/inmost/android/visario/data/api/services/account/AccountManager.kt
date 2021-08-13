package pro.inmost.android.visario.data.api.services.account

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.data.api.dto.requests.UpdateProfileRequest
import pro.inmost.android.visario.data.api.dto.responses.StandardResponse
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.api.utils.logInfo
import pro.inmost.android.visario.data.entities.ProfileData
import java.io.File

class AccountManager(private val service: AccountService) {

    suspend fun getCurrentUserProfile(): Result<ProfileData> = withContext(IO) {
        kotlin.runCatching {
            val response = service.getUserProfile()
            logInfo("getProfile response: $response")
            Result.success(response)
        }.getOrElse {
            logError("getProfile error: ${it.message}")
            Result.failure(it)
        }
    }

    suspend fun updateProfile(data: ProfileData): Result<Unit> = withContext(IO){
        kotlin.runCatching {
            val request = UpdateProfileRequest(
                username = data.username,
                firstName = data.firstName,
                lastName = data.lastName,
                birthday = data.birthday,
                about = data.about,
                showEmailTo = data.showEmailTo,
                showPhoneNumberTo = data.showPhoneNumberTo
            )

            val response = service.updateProfile(request)
            logInfo("updateProfile response: $response")
            if (response.status == STATUS_OK){
                Result.success(Unit)
            } else {
                logError(response.toString())
                Result.failure(Throwable(response.toString()))
            }
        }.getOrElse {
            logError("updateProfile error: ${it.message}")
            Result.failure(it)
        }
    }

    fun updateProfileImage(file: File): Result<Unit> {
        val bytes = file.readBytes()
        // TODO
        return Result.success(Unit)
    }
}