package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.exceptions.UserAlreadyExists
import cz.foglas.enligsh.wordApp.repository.UserRepo
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class AuthenticationService(
    val userRepo: UserRepo,
    val authenticationManager: ReactiveAuthenticationManager,
    val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationServiceInf {


    override fun register(user: User): User {
        if (userRepo.existsUserByEmail(user.email)) {
            throw UserAlreadyExists(user.email)
        }

        user.apply { this.securityPassword = passwordEncoder.encode(securityPassword) }
        return userRepo.save(user)
    }

    override fun login(email: String, password: String): Mono<Authentication> {
        return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        // return userRepo.findByEmail(email)?:throw UsernameNotFoundException("Not found")
    }


    override fun logout(token: String) {
        TODO("Not yet implemented")
    }

}

