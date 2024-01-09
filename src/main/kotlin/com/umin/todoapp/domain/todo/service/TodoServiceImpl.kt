package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.dto.DeleteCommentRequest
import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.domain.comment.repository.CommentRepository
import com.umin.todoapp.domain.todo.dto.TodoRequest
import com.umin.todoapp.domain.todo.dto.TodoResponse
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.todo.dto.TodoWithCommentsResponse
import com.umin.todoapp.domain.todo.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository
): TodoService {

    override fun createTodo(request: TodoRequest): TodoResponse {
        validateInput(request.title, request.description)

        return TodoResponse.from(
            todoRepository.save(request.to()))
    }

    override fun getTodoList(sort: String?, writer: String?): List<TodoResponse> {
        val todoList = when (sort) {
            "asc" -> {
                todoRepository.findAllByOrderByCreatedAt()
            }
            // 기본 정렬 - 작성일 기준 내림차순
            else -> {
                todoRepository.findAllByOrderByCreatedAtDesc()
            }
        }.map { TodoResponse.from(it) }
        return writer?.let { todoList.filter { todo -> todo.writer == it } }?: todoList
    }

    override fun getTodoById(todoId: Long): TodoWithCommentsResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return TodoWithCommentsResponse.from(todo)
    }

    override fun updateTodo(todoId: Long, request: TodoRequest): TodoResponse {
        validateInput(request.title, request.description)

        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val (title, description, writer) = request
        todo.title = title
        todo.description = description
        todo.writer = writer

        return TodoResponse.from(todoRepository.save(todo))
    }

    override fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        todoRepository.delete(todo)
    }

    @Transactional
    override fun updateTodoCompletionStatus(todoId: Long, statusRequest: Boolean): TodoResponse {
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
        return TodoResponse.from(todo)
    }

    override fun createComment(todoId: Long, request: CommentRequest): CommentResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val comment = Comment(
            content = request.content,
            writer = request.writer,
            password = request.password,
            todo = todo
        )
        todo.addComment(comment)
        todoRepository.save(todo)

        return CommentResponse.from(comment)
    }

    override fun updateComment(todoId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        val comment =
            commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (!comment.checkIfWriter(request.writer, request.password)) {
            throw IllegalArgumentException("Writer name or password does not match")
        }

        comment.content = request.content

        return CommentResponse.from(commentRepository.save(comment))
    }

    override fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (!comment.checkIfWriter(request.writer, request.password)) {
            throw IllegalArgumentException("Writer name or password does not match")
        }

        todo.removeComment(comment)

        todoRepository.save(todo)
    }

    private fun validateInput(title: String, description: String) {
        if (title.isEmpty() || title.length > 200) {
            throw IllegalArgumentException("Title must be between 1 and 200 characters.")
        }
        if (description.isEmpty() || description.length > 1000) {
            throw IllegalArgumentException("Description must be between 1 and 1000 characters.")
        }
    }

}