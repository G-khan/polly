package com.gokhana.polly.controller

import com.gokhana.polly.model.dto.BaseResponse
import com.gokhana.polly.model.dto.JwtAuthenticationResponse
import com.gokhana.polly.model.dto.LoginRequest
import com.gokhana.polly.model.dto.SignUpRequest
import com.gokhana.polly.model.entity.User
import com.gokhana.polly.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController @Autowired
constructor(private val authenticationService: AuthenticationService, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("/signin")
    fun loginUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<*> {
        val jwt = authenticationService.authenticateUser(loginRequest)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignUpRequest): ResponseEntity<*> {
        // Creating user's account
        val user = User(
            name = signUpRequest.name, username = signUpRequest.username,
            email = signUpRequest.email, password = passwordEncoder.encode(signUpRequest.password)
        )
        val location = authenticationService.registerUser(user)
        return ResponseEntity.created(location).body<Any>(BaseResponse(true, "User registered successfully"))
    }
}