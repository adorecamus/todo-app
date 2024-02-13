package com.umin.todoapp.domain.todo.repository

import com.umin.todoapp.domain.config.RepositoryConfig
import com.umin.todoapp.domain.todo.dto.TodoSearchRequest
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.user.model.User
import com.umin.todoapp.domain.user.repository.IUserRepository
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import java.time.OffsetDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [RepositoryConfig::class])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
class TodoRepositoryTest (
    private val todoRepository: ITodoRepository,
    private val userRepository: IUserRepository
) : ExpectSpec({
    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    context("Todo 목록 pagination 조회") {
        val user1 = User(email = "sample1@naver.com", password = "aaaa", name = "김문어")
        val user2 = User(email = "sample2@gmail.com", password = "aaaa", name = "김사과")
        val user3 = User(email = "sample3@daum.net", password = "aaaa", name = "박문어")
        val userList = listOf(user1, user2, user3)
        userList.forEach { userRepository.save(it) }

        val todoList = listOf(
            Todo(title = "투두", description = "a", user = user1, createdAt = OffsetDateTime.now().minusDays(6)),
            Todo(title = "제목", description = "a", user = user1, createdAt = OffsetDateTime.now().minusDays(5)),
            Todo(title = "투두", description = "a", user = user1, createdAt = OffsetDateTime.now().minusDays(4), completionStatus = true),
            Todo(title = "투두", description = "a", user = user2, createdAt = OffsetDateTime.now().minusDays(3)),
            Todo(title = "제목", description = "a", user = user2, createdAt = OffsetDateTime.now().minusDays(2), completionStatus = true),
            Todo(title = "투두", description = "a", user = user2, createdAt = OffsetDateTime.now().minusDays(1)),
            Todo(title = "투두", description = "a", user = user3, createdAt = OffsetDateTime.now().minusHours(20)),
            Todo(title = "제목", description = "a", user = user3, createdAt = OffsetDateTime.now().minusHours(15), completionStatus = true),
            Todo(title = "투두", description = "a", user = user3, createdAt = OffsetDateTime.now().minusHours(10)),
            Todo(title = "제목", description = "a", user = user3, createdAt = OffsetDateTime.now().minusHours(5))
        )
        todoList.forEach { todoRepository.save(it) }

        expect("pageNumber와 pageSize에 따른 totalPages와 pageResult를 반환한다") {
            val actual = todoRepository.getPaginatedTodoList(1, 6, null, null)

            actual.totalPages shouldBe 2
            actual.pageResult.size shouldBe 6
        }

        expect("sort에 따라 정렬된 결과를 반환한다") {
            val actual = todoRepository.getPaginatedTodoList(1, 10, "oldest", null)

            for (i in 1 until actual.pageResult.size) {
                actual.pageResult[i].createdAt.isBefore(actual.pageResult[i-1].createdAt) shouldBe false
            }
        }

        expect("제목에 키워드가 포함된 Todo를 조회한다") {
            val keyword = "제목"
            val actual = todoRepository.getPaginatedTodoList(
                1,
                10,
                null,
                TodoSearchRequest(title = keyword, writer = null, completed = null, daysAgo = null)
            )

            actual.pageResult.size shouldBe 4
            actual.pageResult.forEach { it.title.contains(keyword) shouldBe true }
        }

        expect("작성자에 키워드가 포함된 Todo를 조회한다") {
            val keyword = "문어"
            val actual = todoRepository.getPaginatedTodoList(
                1,
                10,
                null,
                TodoSearchRequest(title = null, writer = keyword, completed = null, daysAgo = null)
            )

            actual.pageResult.size shouldBe 7
            actual.pageResult.forEach { it.writer.contains(keyword) shouldBe true }
        }

        expect("완료 상태가 일치하는 Todo만 조회한다") {
            val actual = todoRepository.getPaginatedTodoList(
                1,
                10,
                null,
                TodoSearchRequest(title = null, writer = null, completed = "true", daysAgo = null)
            )

            actual.pageResult.size shouldBe 3
            actual.pageResult.forEach { it.completionStatus shouldBe true }
        }

        expect("N일 안에 작성된 Todo만 조회한다") {
            val actual = todoRepository.getPaginatedTodoList(
                1,
                10,
                null,
                TodoSearchRequest(title = null, writer = null, completed = null, daysAgo = "2")
            )

            actual.pageResult.size shouldBe 6
        }

    }
})
