package com.umin.todoapp.domain.comment.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.dto.DeleteCommentRequest
import org.springframework.stereotype.Service

@Service
class CommentService {

    fun createComment(todoId: Long, request: CommentRequest): CommentResponse {
        TODO()
    }

    fun updateComment(todoId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        TODO()
    }

    fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest): CommentResponse {
        TODO()
    }

}