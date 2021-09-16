package pro.inmost.android.visario.domain.usecases.auth

import pro.inmost.android.visario.domain.entities.user.Credentials

interface UpdateCredentialsUseCase {

    /**
     * Update user's [Credentials] in AWS Chime API
     *
     * @param credentials
     */
    fun update(credentials: Credentials)
}