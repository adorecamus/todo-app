package com.umin.todoapp.domain.controller

import com.umin.todoapp.domain.dto.TodoRequest
import com.umin.todoapp.domain.dto.TodoResponse
import com.umin.todoapp.domain.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    fun createTodo(@RequestBody request: TodoRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(request))
    }

    @GetMapping
    fun getTodoList(): ResponseEntity<List<TodoResponse>> {
        TODO()
    }

    @GetMapping("/{todoId}")
    fun getTodo(@PathVariable todoId: Long): ResponseEntity<TodoResponse> {
        TODO()
    }

    @PutMapping("/{todoId}")
    fun updateTodo(@PathVariable todoId: Long, @RequestBody request: TodoRequest): ResponseEntity<TodoResponse> {
        TODO()
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long): ResponseEntity<Unit> {
        TODO()
    }

}