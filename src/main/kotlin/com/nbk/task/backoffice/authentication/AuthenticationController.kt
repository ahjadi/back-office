package com.nbk.task.backoffice.authentication

import com.nbk.task.backoffice.authentication.jwt.JwtService
import com.nbk.task.backoffice.dto.CreateUserRequest
import com.nbk.task.backoffice.dto.CreateUserResponse
import com.nbk.task.backoffice.dto.LoginRequest
import com.nbk.task.backoffice.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val userService: UserService,
) {

    @PostMapping("/register")
    fun register(@RequestBody user: CreateUserRequest): CreateUserResponse {
        return userService.create(user.username, user.password)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: LoginRequest): TokenResponse {
        val authToken = UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        val authentication = authenticationManager.authenticate(authToken)

        if (authentication.isAuthenticated) {
            val userDetails = userDetailsService.loadUserByUsername(authRequest.username)
            val token = jwtService.generateToken(userDetails.username)
            return TokenResponse(token)
        } else {
            throw UsernameNotFoundException("Invalid user request!")
        }
    }
}