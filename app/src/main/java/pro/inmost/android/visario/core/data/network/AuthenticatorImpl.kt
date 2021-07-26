package pro.inmost.android.visario.core.data.network

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import pro.inmost.android.visario.core.domain.entities.auth.AuthResponse
import pro.inmost.android.visario.core.domain.entities.auth.LoginRequest
import pro.inmost.android.visario.core.domain.entities.auth.RegisterRequest
import pro.inmost.android.visario.core.domain.entities.auth.Tokens
import pro.inmost.android.visario.core.domain.utils.RESPONSE_OK
import pro.inmost.android.visario.core.domain.utils.SERVER_BASE_URL
import pro.inmost.android.visario.core.domain.utils.log
import pro.inmost.android.visario.ui.screens.auth.Authenticator

class AuthenticatorImpl : Authenticator, KoinComponent {
    private val loginUrl = "${SERVER_BASE_URL}auth/login"
    private val registerUrl = "${SERVER_BASE_URL}auth/register"
    private val tokensHolder: TokensHolder by inject()

    override suspend fun login(request: LoginRequest): AuthResponse {
        val gson = Gson()
        val client = OkHttpClient()

        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val json = gson.toJson(request)

        val body: RequestBody = json.toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(loginUrl)
            .post(body)
            .build()
        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: ""
            log("response body: $body")

            return if (body.contains("accessToken")){
                val tokens = gson.fromJson(body, Tokens::class.java)
                tokensHolder.updateTokens(tokens)
                AuthResponse.OK(tokens)
            } else {
                gson.fromJson(body, AuthResponse.Error::class.java)
            }
        }
    }

    override suspend fun logout() {
        tokensHolder.deleteTokens()
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        val client = OkHttpClient()
        val gson = Gson()

        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val json = gson.toJson(request)

        val body: RequestBody = json.toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(registerUrl)
            .post(body)
            .build()
        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: ""
            log("response body: $body")
            return if (body == RESPONSE_OK || body.isBlank()){
                AuthResponse.OK()
            } else {
                gson.fromJson(body, AuthResponse.Error::class.java)
            }
        }
    }
}