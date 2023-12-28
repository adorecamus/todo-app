package com.umin.todoapp.domain.model

import com.umin.todoapp.domain.dto.TodoResponse
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "todo")
class Todo(
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "writer")
    var writer: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        description = description,
        createdAt = createdAt,
        writer = writer
    )
}