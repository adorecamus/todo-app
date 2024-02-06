package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.todo.dto.*

interface TodoService {
    fun createTodo(request: TodoRequest, userId: Long): TodoResponse

    fun getTodoList(sort: String?, writer: String?): List<TodoWithCommentsResponse>

    fun getPaginatedTodoList(
        pageNumber: Int,
        pageSize: Int,
        sort: String?,
        request: TodoSearchRequest
    ): TodoPageResponse

    fun getVisitedTodoList(userId: Long): List<TodoResponse>

    fun getTodoById(todoId: Long, userId: Long): TodoWithCommentsResponse

    fun updateTodo(todoId: Long, request: TodoRequest, userId: Long): TodoResponse

    fun deleteTodo(todoId: Long, userId: Long)

    fun updateTodoCompletionStatus(todoId: Long, statusRequest: Boolean, userId: Long): TodoResponse

    fun createComment(todoId: Long, request: CommentRequest, userId: Long): CommentResponse

    fun updateComment(todoId: Long, commentId: Long, request: CommentRequest, userId: Long): CommentResponse

    fun deleteComment(todoId: Long, commentId: Long, userId: Long)
}