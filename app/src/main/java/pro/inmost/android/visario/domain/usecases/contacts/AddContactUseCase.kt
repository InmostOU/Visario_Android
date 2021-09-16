package pro.inmost.android.visario.domain.usecases.contacts


/**
 * Add contact use case
 *
 */
interface AddContactUseCase {
    /**
     * Add contact to personal user's contact list
     *
     * @param username user's unique username
     * @return [Result]
     */
    suspend fun add(username: String): Result<Unit>
}