package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.domain.comment.repository.ICommentRepository
import com.umin.todoapp.domain.exception.NotPermittedException
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.todo.dto.*
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.todo.repository.ITodoRepository
import com.umin.todoapp.domain.todo.repository.TodoRedisRepository
import com.umin.todoapp.domain.user.repository.IUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: ITodoRepository,
    private val commentRepository: ICommentRepository,
    private val userRepository: IUserRepository,
    private val todoRedisRepository: TodoRedisRepository
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

    override fun getTodoList(sort: String?, writer: String?): List<TodoWithCommentsResponse> {

        return todoRepository.getTodoList(sort, writer)
            .map { TodoWithCommentsResponse.from(it) }
    }

    override fun getPaginatedTodoList(
        pageNumber: Int,
        pageSize: Int,
        sort: String?,
        request: TodoSearchRequest
    ): TodoPageResponse {

        return todoRepository.getPaginatedTodoList(pageNumber, pageSize, sort, request)
    }

    override fun getVisitedTodoList(userId: Long): List<TodoResponse>? {

        return todoRedisRepository.getVisitedTodoIdList(userId)?.let { todoIds ->
            val todoList = todoRepository.getTodoListByIds(todoIds).map { TodoResponse.from(it) }

            todoIds.map { todoId -> todoList.first { it.id == todoId } }
        }
    }

    override fun getTodoById(todoId: Long, userId: Long): TodoWithCommentsResponse {

        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        todoRedisRepository.saveVisitedTodo(userId, todoId)

        return TodoWithCommentsResponse.from(todo)
    }

    @Transactional
    override fun updateTodo(todoId: Long, request: TodoRequest, userId: Long): TodoResponse {

        val todo = getTodoIfAuthorized(todoId, userId)

        todo.update(request.title, request.description)

        return TodoResponse.from(todo)
    }

    override fun deleteTodo(todoId: Long, userId: Long) {

        val todo = getTodoIfAuthorized(todoId, userId)

        todoRepository.delete(todo)
    }

    @Transactional
    override fun updateTodoCompletionStatus(todoId: Long, statusRequest: Boolean, userId: Long): TodoResponse {

        val todo = getTodoIfAuthorized(todoId, userId)

        check(todo.isNotSameStatusWith(statusRequest)) {
            "Todo $todoId completion Status is already $statusRequest"
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
    override fun updateComment(todoId: Long, commentId: Long, request: CommentRequest, userId: Long): CommentResponse {

        todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val comment = getCommentIfAuthorized(commentId, userId)

        comment.update(request.content)

        return CommentResponse.from(comment)
    }

    override fun deleteComment(todoId: Long, commentId: Long, userId: Long) {

        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val comment = getCommentIfAuthorized(commentId, userId)

        todo.removeComment(comment)

        todoRepository.save(todo)
    }

    private fun getTodoIfAuthorized(todoId: Long, userId: Long): Todo {

        val todo = todoRepository.findById(todoId) ?: throw ModelNotFoundException("Todo", todoId)

        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("User", userId)

        if (!todo.matchUserIdWith(user.id!!)) {
            throw NotPermittedException(user.id, "Todo", todo.id!!)
        }

        return todo
    }

    private fun getCommentIfAuthorized(commentId: Long, userId: Long): Comment {

        val comment = commentRepository.findById(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("User", userId)

        if (!comment.matchUserIdWith(user.id!!)) {
            throw NotPermittedException(user.id, "Comment", comment.id!!)
        }

        return comment
    }

}