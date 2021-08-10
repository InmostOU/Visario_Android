package pro.inmost.android.visario.data.api.services.account

import pro.inmost.android.visario.data.api.services.account.AccountService

class AccountManager(private val service: AccountService) {

    suspend fun getCurrentUserInfo(){
        val response = service.getUserInfo()
    }
}