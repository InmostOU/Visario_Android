package pro.inmost.android.visario.domain.usecases.auth

import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.domain.repositories.AccountRepository

class UpdateTokensUseCaseImpl(private val repository: AccountRepository) : UpdateTokensUseCase {
    override fun update(tokens: Tokens) {
        repository.updateTokens(tokens)
    }
}