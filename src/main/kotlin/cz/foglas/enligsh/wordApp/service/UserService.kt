package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.repository.UserRepo
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepo: UserRepo
) {

    fun getUserByEmail(email: String): User? {
        return userRepo.findByEmail(email)
    }
}