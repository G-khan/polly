package com.gokhana.polly.model.dto

import javax.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val usernameOrEmail:String ,
    @field:NotBlank
    val password: String
)
