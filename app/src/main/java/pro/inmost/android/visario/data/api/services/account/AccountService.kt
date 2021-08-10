package pro.inmost.android.visario.data.api.services.account

import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.api.dto.responses.UserInfoResponse
import retrofit2.http.GET

interface AccountService {
    @GET(Endpoints.USER_INFO)
    suspend fun getUserInfo(): UserInfoResponse
}