package pro.inmost.android.visario.data.network.chimeapi.session

import pro.inmost.android.visario.data.network.chimeapi.services.SessionService

class SessionManager(private val service: SessionService) {
    suspend fun sessionConnect(request: String) = service.sessionConnect(request)
}