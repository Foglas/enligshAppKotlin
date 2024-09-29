package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.repository.UserRepo
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import reactor.core.publisher.Mono

@Configuration
open class ApplicationUserConfig(
   val userRepo: UserRepo
) {
    val logger = KotlinLogging.logger("appuserconfig")
    @Bean
    open fun userDetailsService(): ReactiveUserDetailsService {
        return ReactiveUserDetailsService { username ->
            Mono.just(userRepo.findByEmail(username)) ?: throw UsernameNotFoundException("User not found")
        }
    }

    @Bean
    open fun bcryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun authenticationProvider():  UserDetailsRepositoryReactiveAuthenticationManager {
        val authProvider = UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService())
        logger.info { "authenticationProvider" }
        authProvider.setPasswordEncoder(bcryptPasswordEncoder())
    logger.info { "manager $authProvider" }
        return authProvider
    }

}