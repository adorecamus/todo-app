package com.umin.todoapp.domain.todo.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class TodoRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    private val recentlyVisitedSize: Long = 5

    fun saveVisitedTodo(userId: Long, todoId: Long) {

        val key = userId.toString()
        val value = todoId.toString()

        redisTemplate.opsForList().remove(key, 0, value)
        redisTemplate.opsForList().leftPush(key, value)

        if (redisTemplate.opsForList().size(key)!! > recentlyVisitedSize) {
            redisTemplate.opsForList().rightPop(key)
        }
    }

    fun getVisitedTodoIdList(userId: Long): List<Long>? {

        return redisTemplate.opsForList().range(userId.toString(), 0, -1)
            ?.map { it.toLong() }
    }

}