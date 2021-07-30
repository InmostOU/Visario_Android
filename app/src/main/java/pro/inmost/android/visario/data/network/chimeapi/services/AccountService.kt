package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.data.network.chimeapi.auth.model.LoginRequestBody
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {

    @POST(URLs.LOGIN)
    suspend fun login(@Body body: LoginRequestBody): AuthResponse

    @POST(URLs.REGISTER)
    suspend fun register(@Body body: RegisterRequest): AuthResponse
}