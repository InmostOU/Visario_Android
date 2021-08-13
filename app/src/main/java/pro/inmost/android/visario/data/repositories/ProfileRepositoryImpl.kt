package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.api.dto.responses.StandardResponse
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.utils.toDataProfile
import pro.inmost.android.visario.data.utils.toDomainProfile
import pro.inmost.android.visario.domain.entities.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import java.io.File

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
            api.user.getCurrentUserProfile().onSuccess {
                withContext(IO) { profileDao.insert(it) }
                result = Result.success(it.toDomainProfile())
            }.onFailure {
                result = Result.failure(it)
            }
        }
        return result ?: Result.failure(Throwable("Get profile: unknown error"))
    }

    override suspend fun updateProfileInfo(profile: Profile): Result<Unit> {
        val profileData = profile.toDataProfile()
        withContext(IO){ profileDao.update(profileData) }
        return api.user.updateProfile(profileData)
    }

    override suspend fun updateProfilePhoto(profile: Profile): Result<Unit> {
        val profileForUpdate = profile.toDataProfile()
        profileDao.update(profileForUpdate)
        return api.user.updateProfileImage(File(profile.image))
    }
}