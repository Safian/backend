package com.safian.backend.auth

import com.safian.multimodul.models.Role
import com.safian.multimodul.models.RoleName
import com.safian.multimodul.models.User
import com.safian.multimodul.service.RoleService
import com.safian.multimodul.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
class AuthApplication {

	@Bean
	fun initDatabase(
		roleService: RoleService,
		userService: UserService,
	) = CommandLineRunner {
		setupInitialDatabase(
			roleService = roleService,
			userService = userService,
			passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(),
		)
	}
}

fun main(args: Array<String>) {
	runApplication<AuthApplication>(*args)
}

private fun setupInitialDatabase(
	roleService: RoleService,
	userService: UserService,
	passwordEncoder: PasswordEncoder,
) {
	roleService.createRoleIfNotExists(role = Role(roleName = RoleName.USER))
	roleService.createRoleIfNotExists(role = Role(roleName = RoleName.ADMIN))

	val userRole = roleService.findRoleByName(RoleName.USER)
	val adminRole = roleService.findRoleByName(RoleName.ADMIN)

	if (userRole != null) {
		userService.createUserIfNotExists(
			User(
				name = "User",
				email = "user@test.com",
				pwd = passwordEncoder.encode("user-password"),
				roles = listOf(userRole),
			),
		)
	}

	if (userRole != null && adminRole != null) {
		userService.createUserIfNotExists(
			User(
				name = "Admin",
				email = "admin@test.com",
				pwd = passwordEncoder.encode("admin-password"),
				roles = listOf(userRole, adminRole),
			),
		)
	}
}

