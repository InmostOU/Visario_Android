package pro.inmost.android.visario.core.data.chimeapi.auth

import pro.inmost.android.visario.core.data.chimeapi.ChimeApi
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.STATUS_OK
import pro.inmost.android.visario.core.data.chimeapi.auth.model.*
import pro.inmost.android.visario.core.data.chimeapi.services.AccountService

class Authenticator(private val service: AccountService) {

    suspend fun login(email: String, password: String): AuthResponse {
        val request = LoginBody(email, password)
        return service.login(request).apply {
            if (status == STATUS_OK && accessToken.isNotBlank()){
                saveTokens(accessToken, refreshToken)
            }
        }
    }

    fun logout(): Boolean {
        ChimeApi.TokensHolder.deleteTokens()
        return true
    }

    suspend fun register(body: RegisterBody): AuthResponse {
        return service.register(body)
    }


    /* fun login(email: String, password: String): AuthResponse {
         val gson = Gson()
         val client = OkHttpClient()
         val loginRequest = LoginBody(email, password)

         val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
         val json = gson.toJson(loginRequest)
         val body: RequestBody = json.toRequestBody(mediaType)

         val request: Request = Request.Builder()
             .url(SERVER_BASE_URL)
             .post(body)
             .build()
         client.newCall(request).execute().use { response ->
             val body = response.body?.string() ?: ""
             log("response body: $body")
             return gson.fromJson(body, AuthResponse::class.java).apply {
                 if (status == STATUS_OK){
                     saveTokens(accessToken, refreshToken)
                 }
             }

         }
     }*/

    private fun saveTokens(accessToken: String, refreshToken: String) {
        ChimeApi.TokensHolder.updateTokens(
            Tokens(
                accessToken,
                refreshToken
            )
        )
    }

    /*fun register(request: RegisterBody): AuthResponse {
        val client = OkHttpClient()
        val gson = Gson()

        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val json = gson.toJson(request)

        val body: RequestBody = json.toRequestBody(mediaType)
        val callRequest: Request = Request.Builder()
            .url(registerUrl)
            .post(body)
            .build()
        client.newCall(callRequest).execute().use { response ->
            val body = response.body?.string() ?: ""
            log("response body: $body")
            return if (body == RESPONSE_OK || body.isBlank()){
                AuthResponse.OK()
            } else {
                gson.fromJson(body, AuthResponse.Error::class.java)
            }
        }
    }*/
}