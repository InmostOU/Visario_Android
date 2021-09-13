package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.user.Profile
import java.io.File

interface ProfileRepository {
    suspend fun observeProfile(): Flow<Profile>
    suspend fun refresh()
    suspend fun updateProfileInfo(profile: Profile): Result<Unit>
    suspend fun uploadProfilePhoto(file: File): Result<Unit>
}