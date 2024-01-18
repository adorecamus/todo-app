package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.dto.DeleteCommentRequest
import com.umin.todoapp.domain.todo.dto.TodoRequest
import com.umin.todoapp.domain.todo.dto.TodoResponse
import com.umin.todoapp.domain.todo.dto.TodoWithCommentsResponse

interface TodoService {
    fun createTodo(request: TodoRequest, userId: Long): TodoResponse

    fun getTodoList(sort: String?, writer: String?): List<TodoResponse>

    fun getTodoById(todoId: Long): TodoWithCommentsResponse

    fun updateTodo(todoId: Long, request: TodoRequest): TodoResponse

    fun deleteTodo(todoId: Long)

    fun updateTodoCompletionStatus(todoId: Long, statusRequest: Boolean): TodoResponse

    fun createComment(todoId: Long, request: CommentRequest): CommentResponse

    fun updateComment(todoId: Long, commentId: Long, request: CommentRequest): CommentResponse

    fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest)
}