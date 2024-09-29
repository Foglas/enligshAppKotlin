package cz.foglas.enligsh.wordApp.security

import cz.foglas.enligsh.wordApp.service.JwtService
import mu.KotlinLogging
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import kotlin.Exception


@Component
class JwtAuthenticationFilter(
    val jwtService: JwtService,
    val authenticationManager:  UserDetailsRepositoryReactiveAuthenticationManager,
    val userDetails : ReactiveUserDetailsService,
) : AuthenticationWebFilter(authenticationManager) {

    val logger = KotlinLogging.logger("auth")


    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authHeader = exchange.request.headers["Authorization"]?.firstOrNull()
        logger.info { "Filter jwt" }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange)
        }
        val token = authHeader.substring(7) // Remove "Bearer " prefix

        return Mono.just(token)
            .flatMap { Mono.just(jwtService.extractUsername(it)) }
            .flatMap { userEmail ->
                userDetails.findByUsername(userEmail)
                    .flatMap { user ->
                        if (jwtService.isValid(token, user)) {
                            val authToken = UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.authorities
                            )
                            val securityContext = SecurityContextImpl(authToken)
                            Mono.just(securityContext)
                        } else {
                            logger.info { "authentification denied" }
                            Mono.empty()
                        }
                    }
            }
            .flatMap { securityContext: SecurityContext ->
                logger.info { "success authentification" }
                chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
            }
    }
}