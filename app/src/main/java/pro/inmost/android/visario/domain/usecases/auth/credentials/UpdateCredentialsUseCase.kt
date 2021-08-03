package pro.inmost.android.visario.domain.usecases.auth.credentials

import pro.inmost.android.visario.domain.entities.Credentials

interface UpdateCredentialsUseCase {
    fun update(credentials: Credentials)
}