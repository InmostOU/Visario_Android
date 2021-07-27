package pro.inmost.android.visario.core.data.chimeapi.services

import pro.inmost.android.visario.core.data.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.core.data.chimeapi.auth.model.LoginBody
import pro.inmost.android.visario.core.data.chimeapi.auth.model.RegisterBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {

    @POST("auth/login")
    suspend fun login(@Body body: LoginBody): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body body: RegisterBody): AuthResponse
}