package pro.inmost.android.visario.data.api.services.account

import okhttp3.MultipartBody
import pro.inmost.android.visario.data.api.dto.requests.profile.UpdateProfileRequest
import pro.inmost.android.visario.data.api.dto.responses.base.BaseResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import pro.inmost.android.visario.data.entities.profile.ProfileData
import retrofit2.http.*


/**
 * Account service to be implemented by retrofit
 *
 */
interface AccountService {
    /**
     * Get current user's profile info
     *
     * @return [ProfileData]
     */
    @GET(Endpoints.PROFILE_GET)
    suspend fun getUserProfile(): ProfileData

    /**
     * Update user's profile info (exclude user's image)
     *
     * @param request [UpdateProfileRequest]
     * @return [BaseResponse]
     */
    @POST(Endpoints.PROFILE_UPDATE)
    suspend fun updateProfile(@Body request: UpdateProfileRequest): BaseResponse

    @Multipart
    @POST(Endpoints.PROFILE_UPLOAD_PHOTO)
    suspend fun uploadPhoto(@Part file: MultipartBody.Part): BaseResponse
}