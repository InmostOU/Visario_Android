package pro.inmost.android.visario.data.api.services.account

import pro.inmost.android.visario.data.api.dto.requests.UpdateProfileRequest
import pro.inmost.android.visario.data.api.dto.responses.StandardResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.profile.ProfileData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {
    @GET(Endpoints.PROFILE_GET)
    suspend fun getUserProfile(): ProfileData

    @POST(Endpoints.PROFILE_UPDATE)
    suspend fun updateProfile(@Body request: UpdateProfileRequest): StandardResponse
}