package pro.inmost.android.visario.ui.boundaries

interface ChatsRepository<T> {
    suspend fun getChats(): Result<List<T>>
}