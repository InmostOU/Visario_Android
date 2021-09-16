package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.user.Profile
import java.io.File


/**
 * Update profile use case
 *
 */
interface UpdateProfileUseCase {
    /**
     * Update profile info
     *
     * @param profile [Profile]
     * @return [Result]
     */
    suspend fun updateInfo(profile: Profile): Result<Unit>

    /**
     * Upload new user's photo
     *
     * @param file [File] with image
     * @return [Result]
     */
    suspend fun uploadImage(file: File): Result<Unit>


    /**
     * Refresh user's profile data
     *
     */
    suspend fun refresh()
}