package com.umin.todoapp.domain.comment.model

import com.umin.todoapp.domain.todo.model.Todo
import com.umin.todoapp.domain.user.model.User
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "comment")
class Comment(

    @Column(name = "content")
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun matchUserIdWith(userId: Long): Boolean {
        return user.id == userId
    }

    fun update(content: String) {
        this.content = content
    }

}
