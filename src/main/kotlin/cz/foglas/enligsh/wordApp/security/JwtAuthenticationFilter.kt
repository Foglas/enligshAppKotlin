package cz.foglas.enligsh.wordApp.security

import cz.foglas.enligsh.wordApp.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.lang.Exception


@Component
class JwtAuthenticationFilter(
    val handlerExceptionResolver: HandlerExceptionResolver,
    val jwtService: JwtService,
    val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    val logger = KotlinLogging.logger("auth")
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader : String? = request.getHeader("Authorization")
        logger.info(authHeader)

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = authHeader.split(" ")[1]
            val userEmail = jwtService.extractUsername(token)

            val authentication = SecurityContextHolder.getContext().authentication

            if (userEmail != null && authentication == null){
                val user = userDetailsService.loadUserByUsername(userEmail)

                if (jwtService.isValid(token, user)){
                   val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }

            filterChain.doFilter(request, response)

        } catch (ex: Exception){
            handlerExceptionResolver.resolveException(request, response, null, ex)
        }
    }
}