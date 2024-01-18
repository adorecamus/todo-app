package com.umin.todoapp.domain.todo.dto

import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.todo.model.Todo
import java.time.OffsetDateTime

data class TodoWithCommentsResponse(
    val id: Long?,
    val title: String,
    val description: String?,
    val createdAt: OffsetDateTime,
    val completionStatus: Boolean,
    val writer: String,
    val comments: List<CommentResponse>?
) {
    companion object {
        fun from(todo: Todo): TodoWithCommentsResponse {
            return TodoWithCommentsResponse(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                createdAt = todo.createdAt,
                completionStatus = todo.completionStatus,
                writer = todo.user.name,
                comments = todo.comments.map { CommentResponse.from(it) }
            )
        }
    }
}
