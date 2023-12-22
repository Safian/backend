package com.safian.multimodul.repositories

import com.safian.multimodul.models.Role
import com.safian.multimodul.models.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun existsByRoleName(roleName: RoleName): Boolean

    fun findByRoleName(roleName: RoleName): Role?
}
