package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
    suspend fun updateProfile(profile: Profile): Result<Unit>
}