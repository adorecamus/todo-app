package com.umin.todoapp.domain.comment.repository

import com.umin.todoapp.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}