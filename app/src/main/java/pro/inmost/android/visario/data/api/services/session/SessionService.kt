package pro.inmost.android.visario.data.api.services.session

import pro.inmost.android.visario.data.api.dto.responses.SessionEndpointResponse
import pro.inmost.android.visario.data.api.services.Endpoints
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface SessionService {
    @GET(Endpoints.SESSION_CONNECT+ "/{id}")
    suspend fun sessionConnect(
        @Url endpoint: String,
        @Path("id") sessionRequest: String
    ): String

    @GET(Endpoints.MESSAGES_SESSION)
    suspend fun getMessagingSessionEndpoint(): SessionEndpointResponse
}