package com.safian.multimodul.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
data class ResourceNotFoundException(
    val msg: String?,
    val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
) : RuntimeException(msg)
