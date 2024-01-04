package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.dto.DeleteCommentRequest
import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.domain.comment.model.toResponse
import com.umin.todoapp.domain.comment.repository.CommentRepository
import com.umin.todoapp.domain.todo.dto.TodoRequest
import com.umin.todoapp.domain.todo.dto.TodoResponse
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.todo.model.toResponse
import com.umin.todoapp.domain.todo.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        return todoRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }
    }

    fun getTodoById(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse()
    }

    @Transactional
    fun updateTodo(todoId: Long, request: TodoRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val (title, description, writer) = request
        todo.title = title
        todo.description = description
        todo.writer = writer

        return todo.toResponse()
    }

    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        todoRepository.delete(todo)
    }

    @Transactional
    fun updateTodoCompletionStatus(todoId: Long, statusRequest: Boolean): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        if (todo.compareStatusWith(statusRequest)) {
            throw IllegalStateException("Completion Status is alreay $statusRequest. todoId: $todoId")
        }

        when (statusRequest) {
            true -> {
                todo.complete()
            }
            false -> {
                todo.setInProgress()
            }
        }
        return todo.toResponse()
    }

    fun createComment(todoId: Long, request: CommentRequest): CommentResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val comment = Comment(
            content = request.content,
            writer = request.writer,
            password = request.password,
            todo = todo
        )
        todo.addComment(comment)
        todoRepository.save(todo)

        return comment.toResponse()
    }

    fun updateComment(todoId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        TODO()
    }

    fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest): CommentResponse {
        TODO()
    }

}