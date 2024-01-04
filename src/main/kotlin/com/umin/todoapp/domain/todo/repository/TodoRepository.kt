package com.umin.todoapp.domain.todo.repository

import com.umin.todoapp.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Todo>
    fun findAllByOrderByCreatedAt(): List<Todo>
}