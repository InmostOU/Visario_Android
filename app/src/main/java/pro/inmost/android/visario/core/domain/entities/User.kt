package pro.inmost.android.visario.core.domain.entities

import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Date
)
