package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.user.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository

class FetchProfileUseCaseImpl(private val repository: ProfileRepository) : FetchProfileUseCase {
    override suspend fun fetch(): Result<Profile> {
        return repository.getProfile()
    }
}