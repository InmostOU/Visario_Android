package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.utils.toDomainProfile
import pro.inmost.android.visario.domain.entities.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao,
    private val api: ChimeApi
) : ProfileRepository {

    override suspend fun getProfile(): Result<Profile>{
        val cachedProfile = withContext(IO) { profileDao.get() }
        var result: Result<Profile>? = null

        if (cachedProfile != null){
            result = Result.success(cachedProfile.toDomainProfile())
        } else {
            api.user.getCurrentUserInfo().onSuccess {
                result = Result.success(it.toDomainProfile())
            }.onFailure {
                result = Result.failure(it)
            }
        }
        return result ?: Result.failure(Throwable("Get profile: unknown error"))
    }

    override suspend fun updateProfile(profile: Profile): Result<Unit> {
        TODO("Not yet implemented")
    }
}