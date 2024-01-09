package com.umin.todoapp.domain.comment.dto

import com.umin.todoapp.domain.comment.model.Comment
import java.time.OffsetDateTime

data class CommentResponse(
    val id: Long?,
    val content: String,
    val writer: String,
    val createdAt: OffsetDateTime
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id,
                content = comment.content,
                writer = comment.writer,
                createdAt = comment.createdAt
            )
        }
    }
}
