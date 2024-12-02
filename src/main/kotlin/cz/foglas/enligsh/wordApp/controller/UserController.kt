package cz.foglas.enligsh.wordApp.controller

import cz.foglas.enligsh.wordApp.service.UserService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("\${englishApp.api.requestPath}")
open class UserController(
    val userService: UserService
) {


    @PostMapping("/public/users/{id}/delete")
    fun deleteUserById(@PathVariable id: Long, user: Principal) {
        userService.deleteUserById(id, user)
    }
}