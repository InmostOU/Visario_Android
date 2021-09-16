package pro.inmost.android.visario.domain.usecases.profile

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.user.Profile


/**
 * Fetch profile use case
 *
 */
interface FetchProfileUseCase {
    /**
     * Observe user's [Profile]
     *
     * @return [Flow] with user's [Profile]
     */
    suspend fun fetch(): Flow<Profile>
}