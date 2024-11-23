package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.data.security.LoginUserDto
import cz.foglas.enligsh.wordApp.data.security.RegisterUserDto
import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.service.AuthenticationServiceInf
import cz.foglas.enligsh.wordApp.service.JwtService
import cz.foglas.enligsh.wordApp.service.UserService
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping( "\${englishApp.api.requestPath}")
class AuthenticationController(
    private val jwtService: JwtService,
    private val authenticationService: AuthenticationServiceInf,
    private val userService: UserService,
) {
    val logger = KotlinLogging.logger("Authentication")


    @PostMapping( "/public/user/register")
    fun register(@Valid @RequestBody user: RegisterUserDto): ResponseEntity<User> {
        logger.info { "register" }
        val user = authenticationService.register(user = User(null, user.nickname, user.email,user.password))
        return ResponseEntity.ok(user)
    }

    @PostMapping("/public/user/login")
    fun login(@RequestBody loginUserDto: LoginUserDto): Mono<ResponseEntity<String>> {
        logger.info { "hello login" }

        val user = authenticationService.login(loginUserDto.email, loginUserDto.password)

        return user.flatMap { user ->
            val userFromDb = userService.getUserByEmail(loginUserDto.email)
            Mono.just(ResponseEntity.ok(jwtService.generateToken(userFromDb!!)))
        }
    }

    @GetMapping("/public/user/token/isValid")
    fun isTokenValid(@RequestBody token: String) : ResponseEntity<Date>{
        val expiration = jwtService.extractExpiration(token)

        return ResponseEntity.ok(expiration)
    }


}