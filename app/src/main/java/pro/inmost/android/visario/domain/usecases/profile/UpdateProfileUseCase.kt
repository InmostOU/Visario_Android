package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.user.Profile

interface UpdateProfileUseCase {
    suspend fun updateInfo(profile: Profile): Result<Unit>
    suspend fun updateImage(profile: Profile): Result<Unit>
}