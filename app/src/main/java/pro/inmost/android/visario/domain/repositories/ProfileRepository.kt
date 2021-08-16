package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.user.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
    suspend fun updateProfileInfo(profile: Profile): Result<Unit>
    suspend fun updateProfilePhoto(profile: Profile): Result<Unit>
}