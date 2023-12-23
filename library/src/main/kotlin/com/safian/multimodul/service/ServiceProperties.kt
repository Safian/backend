package com.safian.multimodul.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix ="service")
data class ServiceProperties (
    val message: String,
    val secret: String,
    val expiration: String)