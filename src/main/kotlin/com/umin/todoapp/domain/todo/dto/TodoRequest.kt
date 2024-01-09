package com.umin.todoapp.domain.todo.dto

import com.umin.todoapp.domain.todo.model.Todo

data class TodoRequest(
    val title: String,
    val description: String,
    val writer: String
) {
    fun to(): Todo {
        return Todo(
            title = title,
            description = description,
            writer = writer
        )
    }
}
