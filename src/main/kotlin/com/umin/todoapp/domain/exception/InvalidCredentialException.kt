package com.umin.todoapp.domain.exception

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException()