package com.umin.todoapp.domain.dto

import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val writer: String
)
