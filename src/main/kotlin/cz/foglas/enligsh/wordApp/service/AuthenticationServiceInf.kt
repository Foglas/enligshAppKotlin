package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User

interface AuthenticationServiceInf {

    fun register(user: User): User
    fun login(username: String, password: String): User
    fun logout(token: String)
}