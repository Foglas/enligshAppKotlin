package cz.foglas.enligsh.wordApp.exceptions

class UserAlreadyExists(
    val email: String
) : Exception() {

    override val message: String
        get() = "User with email $email already exists"

}