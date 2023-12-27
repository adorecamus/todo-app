package com.umin.todoapp.domain.service

import com.umin.todoapp.domain.dto.TodoRequest
import com.umin.todoapp.domain.dto.TodoResponse
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.model.Todo
import com.umin.todoapp.domain.model.toResponse
import com.umin.todoapp.domain.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {

    fun createTodo(request: TodoRequest): TodoResponse {
        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                writer = request.writer
            )
        ).toResponse()
    }

    fun getTodoList(): List<TodoResponse> {
        return todoRepository.findAll().map { it.toResponse() }
    }

    fun getTodoById(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse()
    }

    fun updateTodo(todoId: Long, request: TodoRequest): TodoResponse {
        TODO()
    }

    fun deleteTodo(todoId: Long) {
        TODO()
    }
}