package com.umin.todoapp.domain.todo.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.umin.todoapp.domain.comment.model.QComment
import com.umin.todoapp.domain.todo.dto.TodoPageResponse
import com.umin.todoapp.domain.todo.dto.TodoResponse
import com.umin.todoapp.domain.todo.dto.TodoSearchRequest
import com.umin.todoapp.domain.todo.model.QTodo
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.user.model.QUser
import com.umin.todoapp.infra.querydsl.QueryDslSupport
import org.springframework.data.repository.findByIdOrNull
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

class TodoRepositoryImpl(
    private val todoJpaRepository: TodoJpaRepository
) : ITodoRepository, QueryDslSupport() {

    private val todo = QTodo.todo
    private val user = QUser.user
    private val comment = QComment.comment

    override fun save(todo: Todo): Todo {
        return todoJpaRepository.save(todo)
    }

    override fun getTodoList(sort: String?, writer: String?): List<Todo> {

        val whereClause = BooleanBuilder()
        writer?.let { whereClause.and(user.name.contains(it)) }

        val query = queryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user, user).fetchJoin()
            .leftJoin(todo.comments, comment)
            .where(whereClause)

        sort?.let { query.orderBy(todo.id.asc()) } ?: query.orderBy(todo.id.desc())

        return query.fetch()
    }

    override fun getPaginatedTodoList(pageNumber: Int, pageSize: Int, sort: String?, request: TodoSearchRequest?): TodoPageResponse {

        val query = queryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user, user).fetchJoin()
            .where(request?.let { allCondition(it) })

        val totalPages = ceil(query.fetch().size / pageSize.toDouble()).toInt()

        when (sort) {
            "oldest" -> query.orderBy(todo.id.asc())
            "title" -> query.orderBy(todo.title.asc())
            null -> query.orderBy(todo.id.desc())
        }

        query
            .offset(((pageNumber - 1)* pageSize).toLong())
            .limit(pageSize.toLong())

        return TodoPageResponse(
            pageResult = query.fetch().map { TodoResponse.from(it) },
            totalPages = totalPages
        )
    }

    private fun allCondition(request: TodoSearchRequest): BooleanBuilder {

        val (title, writer, completed, daysAgo) = request

        return BooleanBuilder()
            .and(titleLike(title))
            .and(writerLike(writer))
            .and(completionStatusEq(completed))
            .and(withInDays(daysAgo))
    }

    private fun titleLike(title: String?): BooleanExpression? {
        return title?.let{ todo.title.contains(title) }
    }

    private fun writerLike(writer: String?): BooleanExpression? {
        return writer?.let { todo.user.name.contains(writer) }
    }

    private fun completionStatusEq(completed: String?): BooleanExpression? {

        val completionStatus = when (completed) {
            "true" -> true
            "false" -> false
            else -> return null
        }
        return todo.completionStatus.eq(completionStatus)
    }

    private fun withInDays(daysAgo: String?): BooleanExpression? {

        return daysAgo?.let {
            try {
                val dayNumber = it.toLong()
                val excludeDate = OffsetDateTime.now().minusDays(dayNumber).truncatedTo(ChronoUnit.DAYS)
                todo.createdAt.after(excludeDate)
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun getTodoListByIds(ids: List<Long>?): List<Todo> {

        return queryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user, user).fetchJoin()
            .where(todo.id.`in`(ids))
            .fetch()
    }

    override fun findById(id: Long): Todo? {
        return todoJpaRepository.findByIdOrNull(id)
    }

    override fun delete(todo: Todo) {
        todoJpaRepository.delete(todo)
    }

}