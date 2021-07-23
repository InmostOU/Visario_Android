package pro.inmost.android.visario.domain.model.entities

import java.util.*

data class User(
    val email: String,
    val password: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Date
)
