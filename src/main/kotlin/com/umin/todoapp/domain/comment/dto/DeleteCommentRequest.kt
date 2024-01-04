package com.umin.todoapp.domain.comment.dto

data class DeleteCommentRequest(
    val writer: String,
    val password: String
)
