package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.chimeapi.auth.model.AuthResponse
import pro.inmost.android.visario.data.network.chimeapi.auth.model.LoginRequest
import pro.inmost.android.visario.data.network.chimeapi.auth.model.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {

    @POST(Endpoints.LOGIN)
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST(Endpoints.REGISTER)
    suspend fun register(@Body body: RegistrationRequest): AuthResponse
}