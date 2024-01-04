package com.umin.todoapp.domain.comment.dto

data class CommentRequest(
    var content: String,
    var writer: String,
    var password: String
)
