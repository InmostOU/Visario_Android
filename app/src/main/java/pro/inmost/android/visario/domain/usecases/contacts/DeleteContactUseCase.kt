package pro.inmost.android.visario.domain.usecases.contacts

interface DeleteContactUseCase {
    suspend fun delete(username: String): Result<Unit>
}