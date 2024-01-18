package com.umin.todoapp.domain.config

import com.umin.todoapp.domain.comment.repository.CommentJpaRepository
import com.umin.todoapp.domain.comment.repository.CommentRepositoryImpl
import com.umin.todoapp.domain.comment.repository.ICommentRepository
import com.umin.todoapp.domain.todo.repository.ITodoRepository
import com.umin.todoapp.domain.todo.repository.TodoJpaRepository
import com.umin.todoapp.domain.todo.repository.TodoRepositoryImpl
import com.umin.todoapp.domain.user.repository.IUserRepository
import com.umin.todoapp.domain.user.repository.UserJpaRepository
import com.umin.todoapp.domain.user.repository.UserRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean
    fun todoRepository(todoJpaRepository: TodoJpaRepository): ITodoRepository {
        return TodoRepositoryImpl(todoJpaRepository)
    }

    @Bean
    fun commentRespository(
        commentJpaRespository: CommentJpaRepository
    ): ICommentRepository {
        return CommentRepositoryImpl(commentJpaRespository)
    }

    @Bean
    fun userRepository(userJpaRepository: UserJpaRepository): IUserRepository {
        return UserRepositoryImpl(userJpaRepository)
    }

}