package com.safian.backend.admin.controllers

import com.safian.multimodul.models.ApiResponse
import com.safian.multimodul.models.User
import com.safian.multimodul.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin/users")
class UsersController (
    private val userService: UserService,
) {

    @GetMapping("")
    fun getUsers(): ResponseEntity<ApiResponse.Success<Map<String, List<User>>>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(ApiResponse.Success(status = true, data = mapOf("users" to users)))
    }
}