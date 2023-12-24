package com.safian.multimodul.security

import com.safian.multimodul.exceptions.InvalidJwtException
import com.safian.multimodul.service.ServiceProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*


@Component
class TokenProvider(
    private val securityProperties: ServiceProperties,
) {
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(securityProperties.getKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun extractEmail(token: String): String {
        return extractClaim(token) { claims -> claims.subject }
    }

    private fun generateTokenFromClaims(
        claims: Claims,
        tokenValidity: Long,
    ): String {
        val now = Date()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuer("AuthServiceApplication")
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidity))
            .signWith(securityProperties.getKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateToken(
        email: String,
        roles: List<String>,
    ): String {
        val claims = Jwts.claims()
            .setSubject(email).apply {
                this["role"] = roles
            }
        return generateTokenFromClaims(claims, securityProperties.expiration.toLong())
    }

    fun generateRefreshToken(claims: Claims): String {
        return generateTokenFromClaims(claims, securityProperties.expiration.toLong())
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(securityProperties.getKey())
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: MalformedJwtException) {
            throw InvalidJwtException("JWT token is malformed.")
        } catch (e: ExpiredJwtException) {
            throw InvalidJwtException("JWT token is expired.")
        } catch (e: Exception) {
            throw InvalidJwtException("JWT token validation failed.")
        }
    }

    fun getTokenFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else {
            null
        }
    }

    fun getClaimsFromToken(token: String): Claims? {
        return Jwts.parserBuilder()
            .setSigningKey(securityProperties.getKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}
