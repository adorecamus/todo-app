package com.umin.todoapp.domain.exception

import com.umin.todoapp.domain.exception.dto.ErrorCode

data class RestApiException(
    val errorCode: ErrorCode
) : RuntimeException()
