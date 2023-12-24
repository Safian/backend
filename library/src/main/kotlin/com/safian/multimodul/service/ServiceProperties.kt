package com.safian.multimodul.service

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import javax.crypto.SecretKey

@ConfigurationProperties(prefix ="service")
data class ServiceProperties (
    val message: String,
    val secret: String,
    val expiration: String) {
    fun getKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}