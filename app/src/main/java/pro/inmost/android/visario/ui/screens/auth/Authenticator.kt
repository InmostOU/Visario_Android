package pro.inmost.android.visario.ui.screens.auth

import pro.inmost.android.visario.domain.model.entities.AuthResponse
import pro.inmost.android.visario.domain.model.entities.LoginRequest
import pro.inmost.android.visario.domain.model.entities.RegisterRequest

interface Authenticator {
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun register(request: RegisterRequest): AuthResponse
}