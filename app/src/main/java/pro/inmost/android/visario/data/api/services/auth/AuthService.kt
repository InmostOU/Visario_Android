package pro.inmost.android.visario.data.api.services.auth

import pro.inmost.android.visario.data.api.dto.requests.auth.FacebookLoginRequest
import pro.inmost.android.visario.data.api.dto.requests.auth.GoogleLoginRequest
import pro.inmost.android.visario.data.api.dto.requests.auth.LoginRequest
import pro.inmost.android.visario.data.api.dto.requests.auth.RegistrationRequest
import pro.inmost.android.visario.data.api.dto.responses.auth.AuthResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Authentication service to be implemented by retrofit
 *
 */
interface AuthService {

    @POST(Endpoints.LOGIN)
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST(Endpoints.LOGIN_FACEBOOK)
    suspend fun loginViaFacebook(@Body body: FacebookLoginRequest): AuthResponse

    @POST(Endpoints.LOGIN_GOOGLE)
    suspend fun loginViaGoogle(@Body body: GoogleLoginRequest): AuthResponse

    @POST(Endpoints.REGISTER)
    suspend fun register(@Body body: RegistrationRequest): AuthResponse

    @GET(Endpoints.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Query("email") email: String): String
}