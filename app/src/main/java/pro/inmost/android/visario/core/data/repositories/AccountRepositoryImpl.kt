package pro.inmost.android.visario.core.data.repositories

import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterBody
import pro.inmost.android.visario.core.data.chimeapi.auth.model.Tokens
import pro.inmost.android.visario.core.domain.entities.RequestResult
import pro.inmost.android.visario.ui.boundaries.AccountRepository

class AccountRepositoryImpl(private val chimeApi: ChimeApi) : AccountRepository<RequestResult> {

    override suspend fun logout(): Boolean {
        return chimeApi.auth.logout()
    }

    override suspend fun register(body: RegisterBody): RequestResult {
        val response = chimeApi.auth.register(body)
        return if (response.status == STATUS_OK) {
            RequestResult.OK(response.message)
        } else RequestResult.Error(response.message)
    }

    override suspend fun login(email: String, password: String): RequestResult {
        val response = chimeApi.auth.login(email, password)
        return if (response.status == STATUS_OK){
            RequestResult.OK(Tokens(response.accessToken, response.refreshToken))
        } else {
            RequestResult.Error(response.message)
        }
    }
}