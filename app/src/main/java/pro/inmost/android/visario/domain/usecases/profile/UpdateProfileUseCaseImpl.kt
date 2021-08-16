package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.user.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository

class UpdateProfileUseCaseImpl(private val repository: ProfileRepository) : UpdateProfileUseCase {
    override suspend fun updateInfo(profile: Profile): Result<Unit> {
        return repository.updateProfileInfo(profile)
    }

    override suspend fun updateImage(profile: Profile): Result<Unit> {
        return repository.updateProfilePhoto(profile)
    }
}