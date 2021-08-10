package pro.inmost.android.visario.data.api.services.auth

import pro.inmost.android.visario.data.api.dto.responses.AuthResponse
import pro.inmost.android.visario.data.api.dto.requests.LoginRequest
import pro.inmost.android.visario.data.api.dto.requests.RegistrationRequest
import pro.inmost.android.visario.data.api.services.Endpoints
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST(Endpoints.LOGIN)
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST(Endpoints.REGISTER)
    suspend fun register(@Body body: RegistrationRequest): AuthResponse
}