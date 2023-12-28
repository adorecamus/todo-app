package com.umin.todoapp.domain.dto

import java.time.OffsetDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: OffsetDateTime,
    val writer: String
)
