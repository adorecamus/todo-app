package com.umin.todoapp.domain.dto

data class TodoRequest(
    val title: String,
    val description: String?,
    val writer: String
)
