package com.umin.todoapp.domain.user.repository

import com.umin.todoapp.domain.user.model.User
import com.umin.todoapp.infra.querydsl.QueryDslSupport

class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
) : IUserRepository, QueryDslSupport() {

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)
    }

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

}