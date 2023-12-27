package com.umin.todoapp.domain.repository

import com.umin.todoapp.domain.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
}