package pro.inmost.android.visario.data.network.chimeapi.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pro.inmost.android.visario.data.network.chimeapi.ChimeApi.Companion.SERVER_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceFactory {
    private val timeoutInSec = 5L
    private val builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    fun <T>getService(service: Class<T>, accessToken: String, baseUrl: String): T {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AccessTokenInterceptor(accessToken))
            .callTimeout(timeoutInSec, TimeUnit.SECONDS)
            .build()
        return builder
            .baseUrl(baseUrl)
            .client(client)
            .build()
            .create(service)
    }

    fun <T>getService(service: Class<T>, baseUrl: String): T {
        return builder
            .baseUrl(baseUrl)
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