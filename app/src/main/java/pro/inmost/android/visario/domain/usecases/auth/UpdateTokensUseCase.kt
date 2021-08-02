package pro.inmost.android.visario.domain.usecases.auth

import pro.inmost.android.visario.data.network.chimeapi.auth.model.Tokens

interface UpdateTokensUseCase {
    fun update(tokens: Tokens)
}