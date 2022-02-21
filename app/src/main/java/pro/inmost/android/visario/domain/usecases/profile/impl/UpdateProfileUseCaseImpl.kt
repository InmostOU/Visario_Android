package pro.inmost.android.visario.domain.usecases.profile.impl

import pro.inmost.android.visario.domain.entities.user.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase
import java.io.File

class UpdateProfileUseCaseImpl(private val repository: ProfileRepository) : UpdateProfileUseCase {
    override suspend fun updateInfo(profile: Profile): Result<Unit> {
        return repository.updateProfileInfo(profile)
    }

    override suspend fun uploadImage(file: File): Result<Unit> {
        return repository.uploadProfilePhoto(file)
    }

    override suspend fun updatePassword(
        currentPass: String,
        newPass: String,
        matchingNewPass: String
    ): Result<Unit> {
        return repository.updatePassword(currentPass, newPass, matchingNewPass)
    }

    override suspend fun refresh(){
        repository.refresh()
    }
}