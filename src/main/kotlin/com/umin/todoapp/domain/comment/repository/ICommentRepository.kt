package com.umin.todoapp.domain.comment.repository

import com.umin.todoapp.domain.comment.model.Comment

interface ICommentRepository {

    fun findById(id: Long): Comment?

}