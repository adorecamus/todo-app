package com.umin.todoapp.domain.comment.dto

data class CommentRequest(
    val content: String,
    val writer: String,
    val password: String
)
