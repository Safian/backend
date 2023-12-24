package com.safian.multimodul.service

import com.safian.multimodul.exceptions.ResourceNotFoundException
import com.safian.multimodul.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email) ?: throw ResourceNotFoundException("User with email $email not found")
    }
}
