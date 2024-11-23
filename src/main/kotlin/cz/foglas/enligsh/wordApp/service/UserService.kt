package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.repository.UserRepo
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepo
) {

    fun getUserById(id: Long): User {
        return userRepo.findById(id).get()
    }

    fun getUserByEmail(email: String): User? {
        return userRepo.findByEmail(email)
    }
}