package com.nbk.task.backoffice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime


data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
)

@ControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(PasswordTooShortException::class)
    fun handlePasswordTooShort(e: PasswordTooShortException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.message)
    }

    @ExceptionHandler(PasswordMissingUppercaseException::class)
    fun handlePasswordMissingUppercase(e: PasswordMissingUppercaseException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.message)
    }

    @ExceptionHandler(PasswordMissingDigitException::class)
    fun handlePasswordMissingDigit(e: PasswordMissingDigitException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.message)
    }

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameAlreadyExists(e: UsernameAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.CONFLICT, e.message)
    }


    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUsernameNotFound(e: UsernameNotFoundException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.message)
    }


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.message)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllOtherExceptions(e: Exception): ResponseEntity<ErrorResponse> {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }

    private fun buildErrorResponse(status: HttpStatus, message: String?): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = status.value(),
            error = status.reasonPhrase,
            message = message
        )
        return ResponseEntity.status(status).body(errorResponse)
    }


}
