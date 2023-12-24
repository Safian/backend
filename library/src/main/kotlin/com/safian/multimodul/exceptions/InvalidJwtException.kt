package com.safian.multimodul.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
data class InvalidJwtException(
    override val message: String,
    val httpStatus: HttpStatus = HttpStatus.UNAUTHORIZED,
) : RuntimeException(message)
