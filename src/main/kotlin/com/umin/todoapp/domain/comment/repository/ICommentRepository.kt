package com.umin.todoapp.domain.comment.repository

import com.umin.todoapp.domain.comment.model.Comment

interface ICommentRepository {

    fun findByTodoIdAndId(todoId:Long, id:Long): Comment?

    fun findById(id: Long): Comment?

}