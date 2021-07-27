package pro.inmost.android.visario.core.data.chimeapi.services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import pro.inmost.android.visario.core.data.chimeapi.ChimeApi.Companion.SERVER_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {
    private val builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_BASE_URL)

    fun <T>getService(service: Class<T>, accessToken: String): T {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AccessTokenInterceptor(accessToken))
            .build()
        return builder
            .client(client)
            .build()
            .create(service)
    }

    fun <T>getService(service: Class<T>): T {
        return builder
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