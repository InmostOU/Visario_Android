package pro.inmost.android.visario.domain.usecases.contacts

interface DeleteContactUseCase {
    suspend fun delete(id: Long): Result<Unit>
}