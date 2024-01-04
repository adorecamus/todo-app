package com.umin.todoapp.domain.comment.dto

import com.umin.todoapp.domain.todo.dto.TodoResponse
import java.time.OffsetDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val writer: String,
    val createdAt: OffsetDateTime,
    val todo: TodoResponse
)
