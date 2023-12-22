package com.safian.backend.auth.controller

import com.safian.backend.auth.model.UserAndToken
import com.safian.backend.auth.model.dto.SignInDto
import com.safian.backend.auth.model.dto.SignUpDto
import com.safian.backend.auth.utility.Constant
import com.safian.backend.auth.utility.EndPoint
import com.safian.backend.auth.services.AuthService
import com.safian.multimodul.models.ApiResponse
import io.jsonwebtoken.Claims
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(EndPoint.AUTH_ROOT_PATH)
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping(EndPoint.SIGN_UP)
    fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<ApiResponse.Success<UserAndToken>> {
        val userAndToken = authService.signUp(signUpDto)
        return ResponseEntity.ok(ApiResponse.Success(true, userAndToken))
    }

    @PostMapping(EndPoint.SIGN_IN)
    fun signIn(@RequestBody signInDto: SignInDto): ResponseEntity<ApiResponse.Success<UserAndToken>> {
        val userAndToken = authService.signIn(signInDto)
        return ResponseEntity.ok(ApiResponse.Success(true, userAndToken))
    }

    @GetMapping(EndPoint.REFRESH_TOKEN)
    fun refreshToken(request: HttpServletRequest): ResponseEntity<ApiResponse.Success<Map<String, String>>> {
        val claims = request.getAttribute(Constant.CLAIMS) as Claims
        val token = authService.getRefreshToken(claims = claims)
        return ResponseEntity.ok(ApiResponse.Success(true, mapOf("token" to token)))
    }
}
