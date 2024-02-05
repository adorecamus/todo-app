package com.umin.todoapp.domain.todo.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class TodoRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun saveVisitedTodo(userId: String, todoId: String) {

        redisTemplate.opsForList().leftPush(userId, todoId)

        if (redisTemplate.opsForList().size(userId)!! > 5) {
            redisTemplate.opsForList().rightPop(userId)
        }
    }

}