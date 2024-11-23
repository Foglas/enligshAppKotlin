package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

interface AuthenticationServiceInf {

    fun register(user: User): User
    fun login(username: String, password: String): Mono<Authentication>
    fun logout(token: String)
}