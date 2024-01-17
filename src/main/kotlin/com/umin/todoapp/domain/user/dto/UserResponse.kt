package com.umin.todoapp.domain.user.dto

import com.umin.todoapp.domain.user.model.User

data class UserResponse(
    val id: Long?,
    val email: String,
    val name: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                email = user.email,
                name = user.name
            )
        }
    }
}