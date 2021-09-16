package pro.inmost.android.visario.domain.usecases.auth

interface LogoutUseCase {
    /**
     * Log out user from the AWS Chime
     *
     */
    suspend fun logout()
}