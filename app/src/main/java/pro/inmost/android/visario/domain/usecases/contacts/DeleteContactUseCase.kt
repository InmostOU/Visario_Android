package pro.inmost.android.visario.domain.usecases.contacts

interface DeleteContactUseCase {
    suspend fun delete(id: Int): Result<Unit>
}