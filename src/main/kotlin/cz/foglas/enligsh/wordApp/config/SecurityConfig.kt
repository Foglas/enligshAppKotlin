package cz.foglas.enligsh.wordApp.config

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Security
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.ServerHttpSecurity.http
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.beans.Customizer

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
open class SecurityConfig{


    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain{
      return http.cors { }
          .csrf { it.disable() }
          .authorizeHttpRequests { auth -> auth
          .requestMatchers("/englishApp/api/public/**").permitAll()
          .requestMatchers("/englishApp/api/private/**").authenticated()
          }
          .sessionManagement {
              it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          }
          .build()
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}