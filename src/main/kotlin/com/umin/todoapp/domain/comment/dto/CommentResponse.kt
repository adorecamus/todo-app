package com.umin.todoapp.domain.comment.dto

import com.umin.todoapp.domain.todo.dto.TodoResponse
import java.time.OffsetDateTime

data class CommentResponse(
    val id: Long,
    var content: String,
    var writer: String,
    var password: String,
    val createdAt: OffsetDateTime,
    val todo: TodoResponse
)
