package com.umin.todoapp.domain.todo.dto

import com.umin.todoapp.domain.todo.model.Todo
import java.time.OffsetDateTime

data class TodoResponse(
    val id: Long?,
    val title: String,
    val description: String,
    val createdAt: OffsetDateTime,
    val completionStatus: Boolean,
    val writer: String
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                createdAt = todo.createdAt,
                completionStatus = todo.completionStatus,
                writer = todo.user.name
            )
        }
    }
}
