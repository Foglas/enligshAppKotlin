package cz.foglas.enligsh.wordApp.data.security

import jakarta.validation.constraints.NotBlank

data class RegisterUserDto(
    @field:NotBlank(message = "Nickname is not fill")
    val nickname: String,
    @field:NotBlank(message = "Email is not fill")
    val email: String,
    @field:NotBlank(message = "Password is not fill")
    val password: String
)
