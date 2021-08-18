package pro.inmost.android.visario.domain.usecases.profile

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.user.Profile

interface FetchProfileUseCase {
    suspend fun fetch(): Flow<Profile>
}