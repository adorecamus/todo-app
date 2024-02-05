package com.umin.todoapp.domain.exception.dto

data class ErrorResponse(
    val code: String,
    val message: String?
)
