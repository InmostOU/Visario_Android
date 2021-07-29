package pro.inmost.android.visario.core.data.chimeapi.services

import pro.inmost.android.visario.core.data.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.core.data.chimeapi.auth.model.LoginRequestBody
import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterRequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestBody): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequestBody): AuthResponse
}