package pro.inmost.android.visario.data.api.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pro.inmost.android.visario.data.api.services.Endpoints.SERVER_BASE_URL
import pro.inmost.android.visario.data.api.services.auth.Authenticator
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Retrofit services factory
 *
 * @property tokensHolder - actual access and refresh tokens
 */
class ServiceFactory(private val tokensHolder: Authenticator.TokensHolder) {
    private val timeoutInSec = 10L
    private val builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    /**
     * Create service instance
     *
     * @param service - class of retrofit's service interface
     * @return service
     */
    fun <T>getService(service: Class<T>): T {
        if (tokensHolder.accessToken.isNotBlank()){
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(tokensHolder.accessToken))
                .callTimeout(timeoutInSec, TimeUnit.SECONDS)
                .build()
            builder.client(client)
        }

        return builder
            .baseUrl(SERVER_BASE_URL)
            .build()
            .create(service)
    }

    private class AccessTokenInterceptor(val accessToken: String) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", accessToken)
                .method(original.method, original.body)
                .build()

            return chain.proceed(request)
        }
    }
}