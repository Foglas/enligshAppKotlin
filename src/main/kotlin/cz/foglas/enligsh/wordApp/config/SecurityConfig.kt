package cz.foglas.enligsh.wordApp.config

import cz.foglas.enligsh.wordApp.security.JwtAuthenticationFilter
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Security
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.beans.Customizer

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
open class SecurityConfig(
    val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebFluxConfigurer {


    @Bean
    open fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {

        return http.cors { }
            .csrf { it.disable() }
            .authorizeExchange { auth ->
                auth
                    .pathMatchers("/englishApp/api/public/**").permitAll()
                    .pathMatchers("/englishApp/api/private/**").authenticated()
            }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHORIZATION)
            .build()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
    }
}








