package pro.inmost.android.visario.domain.repositories

import pro.inmost.android.visario.domain.entities.user.Credentials
import pro.inmost.android.visario.domain.entities.user.Register

interface AccountRepository {

    /**
     * Login existing user
     *
     * @param email user's email address
     * @param password user's password
     * @return user's [Credentials]
     */
    suspend fun login(email: String, password: String): Result<Credentials>

    suspend fun loginViaFacebook(token: String): Result<Credentials>

    suspend fun loginViaGoogle(token: String): Result<Credentials>
    /**
     * Register new user
     *
     * @return true if logout successfully
     */
    suspend fun logout(): Boolean

    /**
     * Register new user
     *
     * @param register
     * @return [Result]
     */
    suspend fun register(register: Register): Result<Unit>

    /**
     * Update user credentials after login
     *
     * @param credentials
     */
    fun updateCredentials(credentials: Credentials)
}