package pro.inmost.android.visario.domain.usecases.profile.impl

import kotlinx.coroutines.flow.Flow
import pro.inmost.android.visario.domain.entities.user.Profile
import pro.inmost.android.visario.domain.repositories.ProfileRepository
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase

class FetchProfileUseCaseImpl(private val repository: ProfileRepository) : FetchProfileUseCase {
    override suspend fun fetch(): Flow<Profile> {
        return repository.observeProfile()
    }
}