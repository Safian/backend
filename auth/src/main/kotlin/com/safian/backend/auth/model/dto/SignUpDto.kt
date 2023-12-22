package com.safian.backend.auth.model.dto

data class SignUpDto(
    var fullName: String,
    var email: String,
    var password: String,
)
