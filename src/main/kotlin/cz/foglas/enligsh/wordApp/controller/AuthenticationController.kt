package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.data.LoginUserDto
import cz.foglas.enligsh.wordApp.data.RegisterUserDto
import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.repository.UserRepo
import cz.foglas.enligsh.wordApp.service.AuthenticationServiceInf
import cz.foglas.enligsh.wordApp.service.JwtService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping( "\${englishApp.api.requestPath}")
class AuthenticationController(
    private val jwtService: JwtService,
    private val authenticationService: AuthenticationServiceInf,
    private val userRepo: UserRepo
) {

    val logger = KotlinLogging.logger("Authentication")
    @PostMapping( "/public/user/register")
    fun register(@RequestBody user: RegisterUserDto): ResponseEntity<User>{
        logger.info { "register" }
        val user = authenticationService.register(user = User(null, user.nickname, user.email,user.password))
        return ResponseEntity.ok(user)
    }

    @PostMapping("/public/user/login")
    fun login(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<String>{
        logger.info { "hello login" }

        val user = authenticationService.login(loginUserDto.email, loginUserDto.password)

        val token = jwtService.generateToken(user)
        return ResponseEntity.ok(token)
    }

    @GetMapping("/public/user/token/isValid")
    fun isTokenValid(@RequestBody token: String) : ResponseEntity<Date>{
        val expiration = jwtService.extractExpiration(token)

        return ResponseEntity.ok(expiration)
    }


}