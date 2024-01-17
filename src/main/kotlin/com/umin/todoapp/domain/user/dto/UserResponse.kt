package com.umin.todoapp.domain.user.dto

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String
)