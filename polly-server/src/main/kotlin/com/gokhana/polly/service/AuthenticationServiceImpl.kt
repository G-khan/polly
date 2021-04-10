package com.gokhana.polly.service

import com.gokhana.polly.enum.RoleType
import com.gokhana.polly.exception.PollyInternalException
import com.gokhana.polly.exception.ValidationException
import com.gokhana.polly.model.dto.LoginRequest
import com.gokhana.polly.model.entity.Role
import com.gokhana.polly.model.entity.User
import com.gokhana.polly.repository.RoleRepository
import com.gokhana.polly.repository.UserRepository
import com.gokhana.polly.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Service
class AuthenticationServiceImpl : AuthenticationService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    override fun authenticateUser(loginRequest: LoginRequest): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.usernameOrEmail, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.generateToken(authentication)
        return jwt
    }

    override fun registerUser(user: User): URI {
        if (userRepository.existsByUsername(user.username)) {
            throw ValidationException("Username is already taken!")
        }
        if (userRepository.existsByEmail(user.email)) {
            throw ValidationException("Email Address already in use!")
        }
        val userRole: Role = roleRepository.findByType(RoleType.ROLE_USER)
            ?: throw PollyInternalException("User Role not set.")
        user.roles = setOf(userRole)
        val result: User = userRepository.save(user)
        val location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/api/users/{username}")
            .buildAndExpand(result.username).toUri()
        return location
    }
}