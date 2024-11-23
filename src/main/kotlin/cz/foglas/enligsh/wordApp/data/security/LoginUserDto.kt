package cz.foglas.enligsh.wordApp.data.security

import jakarta.validation.constraints.NotBlank

data class LoginUserDto(
    @field:NotBlank(message = "Nickname is not fill")
    val email: String,
    @field:NotBlank(message = "Nickname is not fill")
    val password: String
)
