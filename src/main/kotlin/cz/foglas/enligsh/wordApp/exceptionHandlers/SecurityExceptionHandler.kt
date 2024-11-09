package cz.foglas.enligsh.wordApp.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import cz.foglas.enligsh.wordApp.data.security.AuthenticationErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-2)
class SecurityExceptionHandler(
    val objectMapper: ObjectMapper,
) : WebExceptionHandler{

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return when (ex) {
            is ExpiredJwtException -> createResponse("JWT token expired", exchange)
            is UnsupportedJwtException -> createResponse("Unsupported version of JWT token", exchange)
            is MalformedJwtException -> createResponse("Bad structure of the JWT token", exchange)
            is io.jsonwebtoken.security.SignatureException -> createResponse("Invalid signature", exchange)
            else -> createResponse("Occur exception while authentication by the token", exchange)
        }
    }

    private fun createResponse(message: String, exchange: ServerWebExchange): Mono<Void>{
        val buffer = exchange.response.bufferFactory().wrap(
            objectMapper.writeValueAsBytes(AuthenticationErrorResponse(message))
        )
        return exchange.response.writeWith(Mono.just(buffer))
    }
}