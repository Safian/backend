package com.safian.backend.auth.security

import com.nimbusds.jose.jwk.JWKSelector
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*


@Bean
fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
}

@Bean
@Throws(NoSuchAlgorithmException::class)
fun jwkSource(): JWKSource<SecurityContext> {
    val rsaKey: RSAKey = generateRsa()
    val jwkSet: JWKSet = JWKSet(rsaKey)
    return JWKSource<SecurityContext> { jwkSelector: JWKSelector, securityContext: SecurityContext? ->
        jwkSelector.select(
            jwkSet
        )
    }
}

@Throws(NoSuchAlgorithmException::class)
private fun generateRsa(): RSAKey {
    val keyPair = generateRsaKey()
    val publicKey: RSAPublicKey = keyPair.public as RSAPublicKey
    val privateKey: RSAPrivateKey = keyPair.private as RSAPrivateKey
    return RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build()
}

@Throws(NoSuchAlgorithmException::class)
private fun generateRsaKey(): KeyPair {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048)
    return keyPairGenerator.generateKeyPair()
}