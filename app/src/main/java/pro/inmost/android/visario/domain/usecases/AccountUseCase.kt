package pro.inmost.android.visario.domain.usecases

import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegisterRequest
import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.domain.boundaries.AccountRepository

class AccountUseCase(private val repository: AccountRepository) {
   suspend fun login(email: String, password: String): Result<Tokens> {
        return repository.login(email, password)
    }

    suspend fun logout(): Boolean {
        return repository.logout()
    }

    suspend fun register(body: RegisterRequest): Result<String> {
        return repository.register(body)
    }
}