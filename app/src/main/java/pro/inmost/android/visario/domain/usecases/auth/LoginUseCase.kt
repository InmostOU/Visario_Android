package pro.inmost.android.visario.domain.usecases.auth

import pro.inmost.android.visario.domain.entities.user.Credentials

interface LoginUseCase {

    /**
     * Login user to the AWS Chime
     *
     * @param email user's actual email address
     * @param password user's password
     * @return [Result] that encapsulates user's [Credentials]
     * or a failure with an arbitrary [Throwable] exception.
     */
    suspend fun login(email: String, password: String): Result<Credentials>

    suspend fun loginViaFacebook(): Result<Int>
}