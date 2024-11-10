package cz.foglas.enligsh.wordApp.security

import cz.foglas.enligsh.wordApp.service.JwtService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


class JwtAuthenticationFilter(
    val jwtService: JwtService,
    authenticationManager: UserDetailsRepositoryReactiveAuthenticationManager,
    val userDetails: ReactiveUserDetailsService
) : AuthenticationWebFilter(authenticationManager) {

    private val logger = KotlinLogging.logger("auth")

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authHeader = exchange.request.headers["Authorization"]?.firstOrNull()
        logger.info { "Filter JWT" }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange)
        }

        val token = authHeader.substring(7) // Remove "Bearer " prefix

        return Mono.just(token)
            .flatMap {
                Mono.just(jwtService.extractUsername(it))
                    .flatMap { userEmail ->
                        userDetails.findByUsername(userEmail)
                            .flatMap { user ->
                                if (jwtService.isValid(token, user)) {
                                    val authToken = UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        user.authorities
                                    )

                                    // Set the authentication context for the current reactive chain
                                    logger.info { "Successfully authenticated: ${user.username}" }

                                    val securityContext = SecurityContextImpl(authToken)
                                    return@flatMap chain.filter(exchange)
                                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken))
                                } else {
                                    logger.warn { "Authentication denied for user: $userEmail" }
                                    return@flatMap Mono.error(ResponseStatusException(HttpStatus.FORBIDDEN))
                                }
                            }
                    }
            }
    }
}