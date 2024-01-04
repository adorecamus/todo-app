package com.umin.todoapp.domain.todo.dto

import com.umin.todoapp.domain.comment.dto.CommentResponse
import java.time.OffsetDateTime

data class TodoWithCommentsResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: OffsetDateTime,
    val writer: String,
    val completionStatus: Boolean,
    val comments: List<CommentResponse>?
)
