package com.gokhana.polly.model.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


data class SignUpRequest(

    @field:Size(min = 4, max = 40)
    @field:NotBlank
    val name: String,

    @field:Size(min = 3, max = 15)
    @field:NotBlank
    val username: String,

    @field:Size(max = 40)
    @field:NotBlank
    @field:Email
    val email: String,

    @field:Size(min = 6, max = 20)
    @field:NotBlank
    val password: @NotBlank String
)