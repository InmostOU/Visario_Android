package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository

class UpdateProfileUseCaseImpl(private val repository: ProfileRepository) : UpdateProfileUseCase {
    override suspend fun update(profile: Profile): Result<Unit> {
        return repository.updateProfile(profile)
    }
}