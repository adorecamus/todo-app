package com.umin.todoapp.domain.todo.repository

import com.umin.todoapp.domain.todo.model.Todo

interface ITodoRepository {

    fun save(todo: Todo): Todo

    fun getTodoList(sort: String?, writer: String?): List<Todo>

    fun getTodoListByIds(ids: List<Long>?): List<Todo>

    fun findById(id: Long): Todo?

    fun delete(todo: Todo)

}