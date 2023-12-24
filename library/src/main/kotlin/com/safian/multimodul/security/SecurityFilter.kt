package com.safian.multimodul.security

import com.safian.multimodul.utility.Constant
import com.safian.multimodul.utility.EndPoint
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(
    private val tokenProvider: TokenProvider,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = tokenProvider.getTokenFromHeader(request)

        if (token != null && tokenProvider.validateToken(token)) {
            val email = tokenProvider.extractEmail(token)
            val userDetails = userDetailsService.loadUserByUsername(email)
            val authentication =
                UsernamePasswordAuthenticationToken(userDetails.username, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        // Set request attribute for refresh toke request
        if (request.requestURI.equals(EndPoint.AUTH_ROOT_PATH + EndPoint.REFRESH_TOKEN)) {
            request.getAttribute(Constant.CLAIMS)
                ?: request.setAttribute(
                    Constant.CLAIMS,
                    tokenProvider.getClaimsFromToken(token!!),
                )
        }

        filterChain.doFilter(request, response)
    }
}
