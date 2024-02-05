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
        redisTemplate.opsForList().leftPush(key, todoId.toString())

        if (redisTemplate.opsForList().size(key)!! > recentlyVisitedSize) {
            redisTemplate.opsForList().rightPop(key)
        }
    }

    fun getVisitedTodoIdList(userId: Long): List<Long>? {

        return redisTemplate.opsForList().range(userId.toString(), 0, recentlyVisitedSize)
            ?.map { it.toLong() }
    }

}