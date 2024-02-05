package com.umin.todoapp.domain.exception.dto

import org.springframework.http.HttpStatus

interface ErrorCode {
    val httpStatus: HttpStatus
    val message: String
    val name: String
}
