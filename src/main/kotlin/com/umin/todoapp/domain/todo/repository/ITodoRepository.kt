package com.umin.todoapp.domain.todo.repository

import com.umin.todoapp.domain.todo.dto.TodoPageResponse
import com.umin.todoapp.domain.todo.dto.TodoSearchRequest
import com.umin.todoapp.domain.todo.model.Todo

interface ITodoRepository {

    fun save(todo: Todo): Todo

    fun getTodoList(sort: String?, writer: String?): List<Todo>

    fun getPaginatedTodoList(pageNumber: Int, pageSize: Int, sort: String?, request: TodoSearchRequest): TodoPageResponse

    fun getTodoListByIds(ids: List<Long>?): List<Todo>

    fun findById(id: Long): Todo?

    fun delete(todo: Todo)

}