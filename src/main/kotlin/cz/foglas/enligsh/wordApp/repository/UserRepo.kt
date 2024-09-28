package cz.foglas.enligsh.wordApp.repository

import cz.foglas.enligsh.wordApp.domains.User
import org.springframework.data.repository.CrudRepository

interface UserRepo : CrudRepository<User, Long>{

    fun findByEmail(email: String): User?
}