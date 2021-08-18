package pro.inmost.android.visario.domain.usecases.profile

import pro.inmost.android.visario.domain.entities.user.Profile
import java.io.File

interface UpdateProfileUseCase {
    suspend fun updateInfo(profile: Profile): Result<Unit>
    suspend fun uploadImage(file: File): Result<Unit>
}