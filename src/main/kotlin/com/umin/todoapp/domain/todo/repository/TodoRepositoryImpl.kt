package com.umin.todoapp.domain.todo.repository

import com.querydsl.core.BooleanBuilder
import com.umin.todoapp.domain.todo.model.QTodo
import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.infra.querydsl.QueryDslSupport
import org.springframework.data.repository.findByIdOrNull

class TodoRepositoryImpl(
    private val todoJpaRepository: TodoJpaRepository
) : ITodoRepository, QueryDslSupport() {

    private val todo = QTodo.todo

    override fun save(todo: Todo): Todo {
        return todoJpaRepository.save(todo)
    }

    override fun getTodoList(sort: String?, writer: String?): List<Todo> {
        TODO("sort 있으면 오름차순, 없으면 내림차순, writer 있으면 검색")
    }

    override fun findById(id: Long): Todo? {
        return todoJpaRepository.findByIdOrNull(id)
    }

    override fun delete(todo: Todo) {
        todoJpaRepository.delete(todo)
    }

}