package com.gokhana.polly.model.dto

data class JwtAuthenticationResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)
