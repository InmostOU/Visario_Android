package pro.inmost.android.visario.domain.usecases.contacts


/**
 * Delete contact use case
 *
 */
interface DeleteContactUseCase {
    /**
     * Delete contact from personal user's contact list
     *
     * @param id user's id
     * @return [Result]
     */
    suspend fun delete(id: Long): Result<Unit>
}