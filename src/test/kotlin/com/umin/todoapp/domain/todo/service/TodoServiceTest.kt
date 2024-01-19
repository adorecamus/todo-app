package com.umin.todoapp.domain.todo.service

import com.umin.todoapp.domain.comment.repository.ICommentRepository
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.todo.repository.ITodoRepository
import com.umin.todoapp.domain.user.repository.IUserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class TodoServiceTest {

    private val todoRepository = mockk<ITodoRepository>()
    private val commentRepository = mockk<ICommentRepository>()
    private val userRepository = mockk<IUserRepository>()

    private val todoService = TodoServiceImpl(todoRepository, commentRepository, userRepository)

    @Test
    fun `Todo 목록이 없을 때 특정 Todo를 요청하면 에러 발생하는지 확인`() {

        // GIVEN
        val todoId = 1L

        // WHEN
        every { todoRepository.findById(any()) } returns null

        // THEN
        shouldThrow<ModelNotFoundException> {
            todoService.getTodoById(todoId)
        }
    }

}