package com.safian.backend.auth.controller

import com.safian.multimodul.models.ApiResponse
import com.safian.multimodul.models.User
import com.safian.multimodul.service.UserService
import com.safian.multimodul.utility.EndPoint
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(EndPoint.USER_ROOT_PATH)
class UserController(
    private val userService: UserService,
) {
    @GetMapping("")
    fun getUsers(): ResponseEntity<ApiResponse.Success<Map<String, List<User>>>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(ApiResponse.Success(status = true, data = mapOf("users" to users)))
    }

    @PostMapping("/upgrade")
    fun upgradeToAdmin(
        authentication: Authentication,
    ): ResponseEntity<ApiResponse.Success<Map<String, User>>> {
        val email = authentication.principal as String
        val user = userService.upgradeToAdmin(email = email)
        return ResponseEntity.ok(ApiResponse.Success(status = true, data = mapOf("user" to user)))
    }
}
