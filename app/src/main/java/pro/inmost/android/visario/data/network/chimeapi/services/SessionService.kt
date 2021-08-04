package pro.inmost.android.visario.data.network.chimeapi.services

import retrofit2.http.GET
import retrofit2.http.Path

interface SessionService {
    @GET(Endpoints.SESSION_CONNECT+ "/{id}")
    suspend fun sessionConnect(
        @Path("id") sessionRequest: String
    ): String
}