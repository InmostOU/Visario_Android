package pro.inmost.android.visario.data.network.user

import pro.inmost.android.visario.data.network.chimeapi.services.UserService

class UserManager(private val service: UserService) {

    suspend fun getCurrentUserInfo() = service.getUserInfo()
}