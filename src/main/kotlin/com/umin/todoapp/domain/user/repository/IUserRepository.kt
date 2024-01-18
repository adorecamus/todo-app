package com.umin.todoapp.domain.user.repository

import com.umin.todoapp.domain.user.model.User

interface IUserRepository {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?

    fun save(user: User): User

    fun findById(id: Long): User?

}