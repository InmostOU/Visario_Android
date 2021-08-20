package pro.inmost.android.visario.data.repositories

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import pro.inmost.android.visario.data.api.ChimeApi
import pro.inmost.android.visario.data.database.dao.ProfileDao
import pro.inmost.android.visario.data.entities.profile.toDataProfile
import pro.inmost.android.visario.data.entities.profile.toDomainProfile
import pro.inmost.android.visario.data.utils.extensions.launchIO
import pro.inmost.android.visario.domain.entities.user.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import java.io.File

class ProfileRepositoryImpl(
    private val profileDao: ProfileDao,
    private val api: ChimeApi
) : ProfileRepository {

    override suspend fun getProfile(): Flow<Profile>{
        launchIO { refresh() }
        return profileDao.getObservable().mapNotNull { it?.toDomainProfile() }
    }

    override suspend fun refresh() {
        api.user.getCurrentUserProfile().onSuccess {
            withContext(IO) { profileDao.insert(it) }
        }
    }

    override suspend fun updateProfileInfo(profile: Profile): Result<Unit> {
        return api.user.updateProfile(profile.toDataProfile()).onSuccess {
            refresh()
        }
    }

    override suspend fun uploadProfilePhoto(file: File): Result<Unit> {
        return api.user.updateProfileImage(file).onSuccess {
            refresh()
        }
    }
}