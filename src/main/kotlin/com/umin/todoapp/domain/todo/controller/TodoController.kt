package com.umin.todoapp.domain.todo.controller

import com.umin.todoapp.domain.todo.dto.*
import com.umin.todoapp.domain.todo.service.TodoService
import com.umin.todoapp.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    fun createTodo(
        @RequestBody request: TodoRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(request, userPrincipal.id))
    }

    @GetMapping
    fun getTodoList(
        @RequestParam(required = false) sort: String?,
        @RequestParam(required = false) writer: String?
    ): ResponseEntity<List<TodoWithCommentsResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoList(sort, writer))
    }

    @GetMapping("/page")
    fun getPaginatedTodoList(
        @RequestParam(defaultValue = "1") pageNumber: Int,
        @RequestParam(defaultValue = "5") pageSize: Int,
        @RequestParam(required = false) sort: String?,
        request: TodoSearchRequest?
    ): ResponseEntity<TodoPageResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getPaginatedTodoList(pageNumber, pageSize, sort, request))
    }

    @GetMapping("/visited")
    fun getVisitedTodoList(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<TodoResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getVisitedTodoList(userPrincipal.id))
    }

    @GetMapping("/{todoId}")
    fun getTodo(
        @PathVariable todoId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<TodoWithCommentsResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoById(todoId, userPrincipal.id))
    }

    @PutMapping("/{todoId}")
    fun updateTodo(
        @PathVariable todoId: Long,
        @RequestBody request: TodoRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, request, userPrincipal.id))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
    ): ResponseEntity<Unit> {
        todoService.deleteTodo(todoId, userPrincipal.id)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PatchMapping("/{todoId}")
    fun updateTodoCompletionStatus(
        @PathVariable todoId: Long,
        @RequestParam completionStatus: Boolean,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodoCompletionStatus(todoId, completionStatus, userPrincipal.id))
    }

}