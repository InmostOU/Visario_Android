package pro.inmost.android.visario.domain.usecases.contacts

interface AddContactUseCase {
    suspend fun add(username: String): Result<Unit>
}