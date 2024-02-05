package com.umin.todoapp.domain.exception

import com.umin.todoapp.domain.exception.dto.CommonErrorCode
import com.umin.todoapp.domain.exception.dto.ErrorCode

data class ModelNotFoundException(
    val modelName: String,
    val id: Long?,
    override val errorCode: ErrorCode = CommonErrorCode.MODEL_NOT_FOUND
) : RestApiException() {

    override val message = id?.let { "$modelName not found with given id: $id" } ?: "$modelName not found"
}