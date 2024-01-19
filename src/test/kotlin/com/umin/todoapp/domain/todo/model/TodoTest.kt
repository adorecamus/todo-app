package com.umin.todoapp.domain.todo.model

import com.umin.todoapp.domain.user.model.User
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Test

class TodoTest {

    val user = mockk<User>()

    @Test
    fun `제목이 200자 이하일 때 저장 가능한지 확인`() {

        // GIVEN
        val title = "하하"

        // WHEN & THEN
        shouldNotThrow<IllegalArgumentException> {
            Todo(
                title = title,
                description = "내용",
                user = user
            ).let {
                it.title shouldBe title
                it.title.length shouldBeLessThanOrEqual 200
            }
        }
    }

    @Test
    fun `제목이 200자 초과일 때 저장 불가능한지 확인`() {

        // GIVEN
        val title = "아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아아"

        // WHEN & THEN
        title.length shouldBeGreaterThan 200
        shouldThrow<IllegalArgumentException> {
            Todo(
                title = title,
                description = "내용",
                user = user
            )
        }
    }

    @Test
    fun `사용자 id의 일치 여부를 반환하는지 확인`() {

        // GIVEN
        val todo = Todo(
            title = "제목",
            description = "내용",
            user = user
        )
        val requestUserId = 1L

        // WHEN
        every { user.id } returns 1L

        // THEN
        todo.compareUserIdWith(requestUserId) shouldBe true
    }

}