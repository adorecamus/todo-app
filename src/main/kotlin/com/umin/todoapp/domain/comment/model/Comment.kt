package com.umin.todoapp.domain.comment.model

import com.umin.todoapp.domain.todo.model.Todo
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "content")
    var content: String,

    @Column(name = "writer")
    val writer: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun checkIfWriter(writer: String, password: String): Boolean {
        return writer == this.writer && password == this.password
    }
}
