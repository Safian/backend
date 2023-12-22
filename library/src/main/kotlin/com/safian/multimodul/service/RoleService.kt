package com.safian.multimodul.service

import com.safian.multimodul.models.Role
import com.safian.multimodul.models.RoleName
import com.safian.multimodul.repositories.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository,
) {
    fun createRoleIfNotExists(role: Role): Role {
        return if (roleRepository.existsByRoleName(role.roleName)) {
            roleRepository.findByRoleName(role.roleName)!!
        } else {
            roleRepository.save(role)
        }
    }

    fun findRoleByName(roleName: RoleName): Role? {
        return roleRepository.findByRoleName(roleName)
    }
}
