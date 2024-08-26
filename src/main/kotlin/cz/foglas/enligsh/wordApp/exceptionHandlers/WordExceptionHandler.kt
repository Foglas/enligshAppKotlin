package cz.foglas.enligsh.wordApp.exceptionHandlers

import cz.foglas.enligsh.wordApp.response.CommonErrorResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WordExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( MethodArgumentNotValidException::class)
    open fun validationViolationHandler(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val fieldErrors = ex.bindingResult.fieldErrors
        return ResponseEntity.badRequest().body(CommonErrorResponse(fieldErrors))
    }
}