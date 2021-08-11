package pro.inmost.android.visario.data.api.services.account

import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.ProfileData
import retrofit2.http.GET

interface AccountService {
    @GET(Endpoints.GET_CURRENT_USER_PROFILE)
    suspend fun getUserProfile(): ProfileData
}