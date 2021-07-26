package pro.inmost.android.visario.data.network

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import pro.inmost.android.visario.domain.model.entities.*
import pro.inmost.android.visario.domain.utils.RESPONSE_OK
import pro.inmost.android.visario.domain.utils.SERVER_BASE_URL
import pro.inmost.android.visario.domain.utils.log
import pro.inmost.android.visario.ui.screens.auth.Authenticator

class AuthenticatorImpl : Authenticator {
    private val loginUrl = "${SERVER_BASE_URL}auth/login"
    private val registerUrl = "${SERVER_BASE_URL}auth/register"
    private val client = OkHttpClient()

    override suspend fun login(request: LoginRequest): AuthResponse {
        val gson = Gson()
        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val json = gson.toJson(request)

        val body: RequestBody = json.toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(loginUrl)
            .post(body)
            .build()
        return withContext(Dispatchers.IO){
            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                log("response body: $body")

                if (body.contains("accessToken")){
                    val tokens = gson.fromJson(body, Tokens::class.java)
                    AuthResponse.OK(tokens)
                } else {
                    gson.fromJson(body, AuthResponse.Error::class.java)
                }
            }
        }
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        val gson = Gson()
        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val json = gson.toJson(request)

        val body: RequestBody = json.toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(registerUrl)
            .post(body)
            .build()
        return withContext(Dispatchers.IO){
            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                if (body == RESPONSE_OK){
                    AuthResponse.OK()
                } else {
                    gson.fromJson(body, AuthResponse.Error::class.java)
                }
            }
        }
    }
}