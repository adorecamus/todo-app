package com.umin.todoapp.domain.exception

import com.umin.todoapp.domain.exception.dto.ErrorCode

abstract class RestApiException : RuntimeException {

    abstract val errorCode: ErrorCode

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(errorCode: ErrorCode) : this(errorCode.message)

}
