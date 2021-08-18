package pro.inmost.android.visario.data.api.services.account

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import pro.inmost.android.visario.data.api.dto.requests.UpdateProfileRequest
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.entities.profile.ProfileData
import pro.inmost.android.visario.ui.utils.log
import java.io.File





class AccountManager(private val service: AccountService) {

    suspend fun getCurrentUserProfile(): Result<ProfileData> = withContext(IO) {
        kotlin.runCatching {
            val response = service.getUserProfile()
            log("profile: $response")
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

    suspend fun updateProfileImage(file: File): Result<Unit> = withContext(IO) {
        kotlin.runCatching {
            val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                file.asRequestBody("image/*".toMediaTypeOrNull())
            )
            service.uploadPhoto(filePart).getResult()
        }.getOrElse {
            logError("upload photo error: ${it.message}")
            Result.failure(it)
        }
    }
}