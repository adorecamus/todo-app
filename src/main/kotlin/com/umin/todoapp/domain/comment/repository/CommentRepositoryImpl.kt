package com.umin.todoapp.domain.comment.repository

import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.infra.querydsl.QueryDslSupport
import org.springframework.data.repository.findByIdOrNull

class CommentRepositoryImpl(
    private val commentJpaRepository: CommentJpaRepository
) : ICommentRepository, QueryDslSupport() {

    override fun findByTodoIdAndId(todoId: Long, id: Long): Comment? {
        return commentJpaRepository.findByTodoIdAndId(todoId, id)
    }

    override fun findById(id: Long): Comment? {
        return commentJpaRepository.findByIdOrNull(id)
    }

}