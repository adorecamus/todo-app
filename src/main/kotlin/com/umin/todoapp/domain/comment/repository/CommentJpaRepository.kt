package com.umin.todoapp.domain.comment.repository

import com.umin.todoapp.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentJpaRepository: JpaRepository<Comment, Long> {

    fun findByTodoIdAndId(todoId: Long, id: Long): Comment?
}