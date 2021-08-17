package pro.inmost.android.visario.data.api.services.account

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.dto.requests.UpdateProfileRequest
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.entities.profile.ProfileData
import java.io.File

class AccountManager(private val service: AccountService) {

    suspend fun getCurrentUserProfile(): Result<ProfileData> = withContext(IO) {
        kotlin.runCatching {
            val response = service.getUserProfile()
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

            service.updateProfile(request).getResult()
        }.getOrElse {
            logError("updateProfile error: ${it.message}")
            Result.failure(it)
        }
    }

    fun updateProfileImage(file: File): Result<Unit> {
        // TODO
        return Result.success(Unit)
    }
}