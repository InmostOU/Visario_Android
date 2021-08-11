package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.Profile

interface UpdateProfileUseCase {
    suspend fun update(profile: Profile): Result<Unit>
}