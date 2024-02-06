package com.umin.todoapp.domain.todo.dto

data class TodoSearchRequest(
    val title: String?,
    val writer: String?,
    val completed: String?,
    val daysAgo: String?
)