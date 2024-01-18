package com.umin.todoapp.domain.todo.repository

import com.querydsl.core.BooleanBuilder
import com.umin.todoapp.domain.todo.model.QTodo
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.user.model.QUser
import com.umin.todoapp.infra.querydsl.QueryDslSupport
import org.springframework.data.repository.findByIdOrNull

class TodoRepositoryImpl(
    private val todoJpaRepository: TodoJpaRepository
) : ITodoRepository, QueryDslSupport() {

    private val todo = QTodo.todo
    private val user = QUser.user

    override fun save(todo: Todo): Todo {
        return todoJpaRepository.save(todo)
    }

    override fun getTodoList(sort: String?, writer: String?): List<Todo> {

        val whereClause = BooleanBuilder()
        writer?.let { whereClause.and(user.name.contains(it)) }

        val query = queryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user, user)
            .fetchJoin()
            .where(whereClause)

        sort?.let { query.orderBy(todo.id.asc()) } ?: query.orderBy(todo.id.desc())

        return query.fetch()
    }

    override fun findById(id: Long): Todo? {
        return todoJpaRepository.findByIdOrNull(id)
    }

    override fun delete(todo: Todo) {
        todoJpaRepository.delete(todo)
    }

}