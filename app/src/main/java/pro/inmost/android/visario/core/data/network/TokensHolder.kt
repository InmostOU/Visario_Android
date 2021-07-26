package pro.inmost.android.visario.core.data.network

import pro.inmost.android.visario.core.domain.entities.auth.Tokens

object TokensHolder {
    private var tokens: Tokens? = null

    fun getAccessToken() = tokens?.accessToken ?: ""

    fun getRefreshToken() = tokens?.refreshToken ?: ""

    fun updateTokens(tokens: Tokens){
        this.tokens = tokens
    }

    fun deleteTokens(){
        tokens = null
    }
}