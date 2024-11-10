package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.security.JwtAuthenticationFilter
import cz.foglas.enligsh.wordApp.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
open class SecurityConfig(
    val jwtService: JwtService,
    val userDetails: ReactiveUserDetailsService,
    val authenticationManager: UserDetailsRepositoryReactiveAuthenticationManager,
) : WebFluxConfigurer {


    @Bean
    open fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {

        return http.cors { }
            .csrf { it.disable() }
            .addFilterAt(
                JwtAuthenticationFilter(jwtService, authenticationManager, userDetails),
                SecurityWebFiltersOrder.AUTHENTICATION
            )
            .authorizeExchange { auth ->
                auth
                    .pathMatchers("/englishApp/api/public/**").permitAll()
                    .pathMatchers("/englishApp/api/private/**").authenticated()
            }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .build()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
    }
}








