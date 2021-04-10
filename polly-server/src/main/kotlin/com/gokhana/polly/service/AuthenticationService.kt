package com.gokhana.polly.service

import com.gokhana.polly.model.dto.LoginRequest
import com.gokhana.polly.model.entity.User
import java.net.URI

interface AuthenticationService {
    fun authenticateUser(loginRequest: LoginRequest): String
    fun registerUser(user: User): URI
}