package com.umin.todoapp.domain.exception

import com.umin.todoapp.domain.exception.dto.CommonErrorCode
import com.umin.todoapp.domain.exception.dto.ErrorCode

data class NotPermittedException(
    val userId: Long,
    val modelName: String,
    val id: Long,
    override val errorCode: ErrorCode = CommonErrorCode.NOT_PERMITTED
): RestApiException(
    "User id $userId not permitted to $modelName id $id"
)