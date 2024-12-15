package cz.foglas.enligsh.wordApp.data.security

data class OutputUserDto(
    val token: String,
    val userId: Long
)