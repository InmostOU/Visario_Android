package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.user.UserInfo
import retrofit2.http.GET

interface UserService {
    @GET(Endpoints.USER_INFO)
    suspend fun getUserInfo(): UserInfo
}