package com.safian.backend.auth.model

import com.safian.multimodul.models.User

data class UserAndToken(
    val user: User,
    val token: String,
)
