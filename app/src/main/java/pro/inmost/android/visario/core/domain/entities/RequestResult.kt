package pro.inmost.android.visario.core.domain.entities

sealed class RequestResult{
    data class OK<T>(val data: T?): RequestResult()
    data class Error(val message: String): RequestResult()
}
