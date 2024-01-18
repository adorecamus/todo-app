package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.dto.DeleteCommentRequest
import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.domain.comment.repository.ICommentRepository
import com.umin.todoapp.domain.exception.ForbiddenException
import com.umin.todoapp.domain.todo.dto.TodoRequest
import com.umin.todoapp.domain.todo.dto.TodoResponse
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.todo.dto.TodoWithCommentsResponse
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.todo.repository.ITodoRepository
import com.umin.todoapp.domain.user.repository.IUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: ITodoRepository,
    private val commentRepository: ICommentRepository,
    private val userRepository: IUserRepository
) : TodoService {

    override fun createTodo(request: TodoRequest, userId: Long): TodoResponse {

        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("User", userId)

        return todoRepository.save(
            Todo(
                title = request.title,
                description = request.description,
                user = user
            )
        ).let { TodoResponse.from(it) }
    }

    override fun getTodoList(sort: String?, writer: String?): List<TodoResponse> {
        return todoRepository.getTodoList(sort, writer).map { TodoResponse.from(it) }
    }

    override fun getTodoById(todoId: Long): TodoWithCommentsResponse {
        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return TodoWithCommentsResponse.from(todo)
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoRequest, userId: Long): TodoResponse {

        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        if (!todo.compareUserIdWith(userId)) {
            throw ForbiddenException(userId, "Todo", todoId)
        }

        todo.changeTodo(request.title, request.description)

        return TodoResponse.from(todo)
    }

    override fun deleteTodo(todoId: Long, userId: Long) {

        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        if (!todo.compareUserIdWith(userId)) {
            throw ForbiddenException(userId, "Todo", todoId)
        }

        todoRepository.delete(todo)
    }

    @Transactional
    override fun updateTodoCompletionStatus(todoId: Long, statusRequest: Boolean): TodoResponse {
        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

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

    override fun createComment(todoId: Long, request: CommentRequest, userId: Long): CommentResponse {

        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("User", userId)

        val comment = Comment(
            content = request.content,
            todo = todo,
            user = user
        )
        todo.addComment(comment)
        todoRepository.save(todo)

        return CommentResponse.from(comment)
    }

    @Transactional
    override fun updateComment(todoId: Long, commentId: Long, request: CommentRequest): CommentResponse {
        val comment =
            commentRepository.findByTodoIdAndId(todoId, commentId) ?: throw ModelNotFoundException("Comment", commentId)

        TODO("작성자 본인인지 확인")

        comment.content = request.content

        return CommentResponse.from(comment)
    }

    override fun deleteComment(todoId: Long, commentId: Long, request: DeleteCommentRequest) {
        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        val comment = commentRepository.findById(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        TODO("작성자 본인인지 확인")

        todo.removeComment(comment)

        todoRepository.save(todo)
    }

}