package com.umin.todoapp.domain.exception

import com.umin.todoapp.domain.exception.dto.CommonErrorCode
import com.umin.todoapp.domain.exception.dto.ErrorCode
import com.umin.todoapp.domain.exception.dto.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<Any> {
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, e.message)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(e: IllegalStateException): ResponseEntity<Any> {
        return handleExceptionInternal(CommonErrorCode.ILLEGAL_STATE, e.message)
    }

    @ExceptionHandler(RestApiException::class)
    fun handleCustomException(e: RestApiException): ResponseEntity<Any> {
        return handleExceptionInternal(e.errorCode, e.message)
    }

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(e: ModelNotFoundException): ResponseEntity<Any> {
        return handleExceptionInternal(CommonErrorCode.MODEL_NOT_FOUND, e.message)
    }

    @ExceptionHandler(InvalidCredentialException::class)
    fun handleInvalidCredential(e: InvalidCredentialException): ResponseEntity<Any> {
        return handleExceptionInternal(CommonErrorCode.INVALID_CREDENTIAL, e.message)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(e: ForbiddenException): ResponseEntity<Any> {
        return handleExceptionInternal(CommonErrorCode.FORBIDDEN, e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllException(e: Exception): ResponseEntity<Any> {
        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR, e.message)
    }

    private fun handleExceptionInternal(errorCode: ErrorCode, message: String?): ResponseEntity<Any> {
        return ResponseEntity.status(errorCode.httpStatus).body(makeErrorResponse(errorCode, message))
    }

    private fun makeErrorResponse(errorCode: ErrorCode, message: String?): ErrorResponse {
        return ErrorResponse(
            code = errorCode.getName(),
            message = message
        )
    }

}