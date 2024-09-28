package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.repository.UserRepo
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
open class ApplicationUserConfig(
   val userRepo: UserRepo
) {
    val logger = KotlinLogging.logger("appuserconfig")
    @Bean
    open fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepo.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
        }
    }

    @Bean
    open fun bcryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @Bean
    open fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        logger.info { "authentication manager" }
        return config.authenticationManager
    }

    @Bean
    open fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        logger.info { "authenticationProvider" }
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(bcryptPasswordEncoder())
        return authProvider
    }

}