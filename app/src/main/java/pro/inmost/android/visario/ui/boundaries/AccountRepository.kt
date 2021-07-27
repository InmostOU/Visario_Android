package pro.inmost.android.visario.ui.boundaries

import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterBody

interface AccountRepository<T> {
    suspend fun login(email: String, password: String): T
    suspend fun logout(): Boolean
    suspend fun register(body: RegisterBody): T
}