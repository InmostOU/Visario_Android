package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.Profile

interface FetchProfileUseCase {
    suspend fun fetch(): Result<Profile>
}