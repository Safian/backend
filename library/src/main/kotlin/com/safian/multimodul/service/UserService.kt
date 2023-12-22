package com.safian.multimodul.service

import com.safian.multimodul.exceptions.ResourceNotFoundException
import com.safian.multimodul.models.RoleName
import com.safian.multimodul.models.User
import com.safian.multimodul.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleService: RoleService,
) {
    fun createUserIfNotExists(user: User): User {
        return if (userRepository.existsByEmail(user.email)) {
            userRepository.findByEmail(user.email)!!
        } else {
            userRepository.save(user)
        }
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun upgradeToAdmin(email: String): User {
        val user =
            userRepository.findByEmail(email) ?: throw ResourceNotFoundException("User with email $email not found")

        val adminRole =
            roleService.findRoleByName(RoleName.ADMIN) ?: throw ResourceNotFoundException("Admin role not found")

        val roles = user.roles.toMutableList()
        roles.add(adminRole)

        return userRepository.save(user.copy(roles = roles))
    }
}
