package com.safian.backend.auth.services

import com.safian.backend.auth.exception.ResourceAlreadyExistException
import com.safian.multimodul.models.RoleName
import com.safian.multimodul.models.User
import com.safian.backend.auth.model.UserAndToken
import com.safian.backend.auth.model.dto.SignInDto
import com.safian.backend.auth.model.dto.SignUpDto
import com.safian.backend.auth.security.TokenProvider
import com.safian.multimodul.exceptions.ResourceNotFoundException
import com.safian.multimodul.repositories.RoleRepository
import com.safian.multimodul.repositories.UserRepository
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tokenProvider: TokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {
    fun signUp(signUpDto: SignUpDto): UserAndToken {
        if (userRepository.existsByEmail(signUpDto.email)) {
            throw ResourceAlreadyExistException("Email ${signUpDto.email} is already in use, please use different email.")
        }

        val role = roleRepository.findByRoleName(RoleName.USER)!!

        val user = userRepository.save(
            User(
                name = signUpDto.fullName,
                email = signUpDto.email,
                pwd = passwordEncoder.encode(signUpDto.password),
                roles = listOf(role),
            ),
        )

        val token = getToken(
            userId = user.id!!,
            username = user.email,
            roles = user.roles.map { it.roleName.name },
        )

        return UserAndToken(user = user, token = token)
    }

    fun signIn(signInDto: SignInDto): UserAndToken {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(signInDto.email, signInDto.password),
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = userRepository.findByEmail(authentication.name)
            ?: throw ResourceNotFoundException("User with email ${signInDto.email} not found")

        val token = getToken(
            userId = user.id!!,
            username = user.email,
            roles = user.roles.map { it.roleName.name },
        )

        return UserAndToken(user = User(), token = token)
    }

    fun getToken(userId: Long, username: String, roles: List<String> = emptyList()): String {
        return tokenProvider.generateToken(email = username, roles = roles)
    }

    fun getRefreshToken(claims: Claims): String {
        return tokenProvider.generateRefreshToken(claims)
    }
}
