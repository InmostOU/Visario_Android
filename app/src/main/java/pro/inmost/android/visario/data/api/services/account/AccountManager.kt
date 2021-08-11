package pro.inmost.android.visario.data.api.services.account

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.utils.logError
import pro.inmost.android.visario.data.entities.ProfileData

class AccountManager(private val service: AccountService) {

    suspend fun getCurrentUserInfo(): Result<ProfileData> = withContext(IO){
        kotlin.runCatching {
            val response = service.getUserProfile()
            Result.success(response)
        }.getOrElse {
            logError("getProfile error: ${it.message}")
            Result.failure(it)
        }
    }
}