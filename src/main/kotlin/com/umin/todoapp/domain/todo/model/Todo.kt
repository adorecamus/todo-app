package com.umin.todoapp.domain.todo.model

import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.domain.todo.dto.TodoResponse
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
    var writer: String,

    @Column(name = "completion_status")
    var completionStatus: Boolean = false,

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun compareStatusWith(statusRequest: Boolean): Boolean {
        return completionStatus == statusRequest
    }

    fun complete() {
        completionStatus = true
    }

    fun setInProgress() {
        completionStatus = false
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun removeComment(comment: Comment) {
        comments.remove(comment)
    }
}

fun Todo.toResponse(): TodoResponse {
    return TodoResponse(
        id = id!!,
        title = title,
        description = description,
        createdAt = createdAt,
        writer = writer,
        completionStatus = completionStatus
    )
}