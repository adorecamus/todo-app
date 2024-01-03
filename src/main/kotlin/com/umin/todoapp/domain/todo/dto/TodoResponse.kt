package com.umin.todoapp.domain.todo.dto

import java.time.OffsetDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: OffsetDateTime,
    val writer: String,
    val completionStatus: Boolean
)
