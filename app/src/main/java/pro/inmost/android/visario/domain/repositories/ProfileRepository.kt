package pro.inmost.android.visario.domain.repositories

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.user.Profile
import java.io.File

interface ProfileRepository {
    /**
     * Observe user's [Profile]
     *
     * @return [Flow] with user's [Profile]
     */
    suspend fun observeProfile(): Flow<Profile>

    /**
     * Refresh user's profile data
     *
     */
    suspend fun refresh()

    /**
     * Update profile info
     *
     * @param profile [Profile]
     * @return [Result] that encapsulates [Profile] or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun updateProfileInfo(profile: Profile): Result<Unit>

    /**
     * Upload new user's photo
     *
     * @param file [File] with image
     * @return [Result]
     */
    suspend fun uploadProfilePhoto(file: File): Result<Unit>

    suspend fun updatePassword(currentPass: String, newPass: String, matchingNewPass: String): Result<Unit>
}