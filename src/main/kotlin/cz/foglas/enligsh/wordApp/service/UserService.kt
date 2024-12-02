package cz.foglas.enligsh.wordApp.service

import cz.foglas.enligsh.wordApp.domains.User
import cz.foglas.enligsh.wordApp.repository.UserRepo
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService(
    private val userRepo: UserRepo
) {

    companion object {
        val logger = KotlinLogging.logger(UserService::class.qualifiedName!!)
    }

    fun deleteUserById(id: Long, user: Principal) {
        logger.info("Deleting user with id {}", id)
        try {
            val userFromDb = userRepo.findById(id).get()

            if (userFromDb.email == user.name) {
                userRepo.deleteById(id)
                logger.info("Successfully deleted user with id {}", id)
            }
        } catch (e: Exception) {
            logger.info("User not found with id {}", id)
            throw UsernameNotFoundException("User with email ${user.name} not found")
        }
    }

    fun getUserById(id: Long): User {
        return userRepo.findById(id).get()
    }

    fun getUserByEmail(email: String): User? {
        return userRepo.findByEmail(email)
    }

}