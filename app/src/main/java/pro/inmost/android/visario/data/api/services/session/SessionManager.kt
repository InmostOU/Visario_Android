package pro.inmost.android.visario.data.api.services.session

class SessionManager(private val service: SessionService) {

    suspend fun sessionConnect(request: String) {
        kotlin.runCatching {
            getSessionEndpoint().onSuccess { endpoint ->
                service.sessionConnect(endpoint, request)
            }
        }

    }

    suspend fun getSessionEndpoint(): Result<String> {
        val response = service.getMessagingSessionEndpoint()
       return if (response.url.isNotBlank()) {
            Result.success(response.url)
        } else {
            Result.failure(Throwable("Error"))
        }
    }
}