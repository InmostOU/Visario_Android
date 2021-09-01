package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.user.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import java.io.File

class UpdateProfileUseCaseImpl(private val repository: ProfileRepository) : UpdateProfileUseCase {
    override suspend fun updateInfo(profile: Profile): Result<Unit> {
        return repository.updateProfileInfo(profile)
    }

    override suspend fun uploadImage(file: File): Result<Unit> {
        return repository.uploadProfilePhoto(file)
    }
}