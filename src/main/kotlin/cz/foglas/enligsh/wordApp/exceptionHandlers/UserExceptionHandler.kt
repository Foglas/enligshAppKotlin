package cz.foglas.enligsh.wordApp.exceptionHandlers

import cz.foglas.enligsh.wordApp.exceptions.UserAlreadyExists
import cz.foglas.enligsh.wordApp.response.CommonErrorTextResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
open class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExists::class)
    open fun notEnoughWordHandler(ex: UserAlreadyExists): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(CommonErrorTextResponse(ex.message))
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException::class)
    open fun notEnoughWordHandler(ex: UsernameNotFoundException): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(CommonErrorTextResponse(ex.message!!))
    }
}