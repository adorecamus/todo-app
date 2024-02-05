package com.umin.todoapp.domain.exception

import com.umin.todoapp.domain.exception.dto.CommonErrorCode
import com.umin.todoapp.domain.exception.dto.ErrorCode

data class InvalidCredentialException(
    override val errorCode: ErrorCode = CommonErrorCode.INVALID_CREDENTIAL
) : RestApiException(errorCode)