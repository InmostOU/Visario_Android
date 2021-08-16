package pro.inmost.android.visario.domain.usecases.auth.credentials

import pro.inmost.android.visario.domain.entities.user.Credentials

interface UpdateCredentialsUseCase {
    fun update(credentials: Credentials)
}