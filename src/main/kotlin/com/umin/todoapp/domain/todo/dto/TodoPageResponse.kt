package com.umin.todoapp.domain.todo.dto

data class TodoPageResponse(
    val pageResult: List<TodoResponse>,
    val totalPages: Int
)