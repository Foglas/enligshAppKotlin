package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.repository.UserRepo
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
   val userRepo: UserRepo,
   val authenticationManager: AuthenticationManager,
   val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationServiceInf {

    override fun register(user: User): User {
        user.apply { this.password = passwordEncoder.encode(password) }
       return userRepo.save(user)
    }

    override fun login(email: String, password: String): User {
       authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        return userRepo.findByEmail(email)?:throw UsernameNotFoundException("Not found")
    }

    override fun logout(token: String) {
        TODO("Not yet implemented")
    }

}