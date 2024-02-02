package com.umin.todoapp.domain.exception.dto

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val httpStatus: HttpStatus, override val message: String
) : ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    ILLEGAL_STATE(HttpStatus.CONFLICT, "Illegal state"),
    MODEL_NOT_FOUND(HttpStatus.NOT_FOUND, "Model not exists"),
    INVALID_CREDENTIAL(HttpStatus.UNAUTHORIZED, "The credential is invalid"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    override fun getName(): String {
        return name
    }
}
