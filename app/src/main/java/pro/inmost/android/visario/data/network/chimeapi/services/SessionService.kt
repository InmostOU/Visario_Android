package pro.inmost.android.visario.data.network.chimeapi.services

import pro.inmost.android.visario.data.network.chimeapi.session.SessionConnectRequest
import retrofit2.http.Body
import retrofit2.http.GET

interface SessionService {
    @GET(Endpoints.SESSION_CONNECT)
    suspend fun sessionConnect(
        @Body sessionRequest: SessionConnectRequest
    ): String
}