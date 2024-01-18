package com.umin.todoapp.domain.todo.model

import com.umin.todoapp.domain.comment.model.Comment
import com.umin.todoapp.domain.user.model.User
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "todo")
class Todo(

    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "completion_status")
    var completionStatus: Boolean = false,

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf()

) {
    init {
        if (title.isEmpty() || title.length > 200) {
            throw IllegalArgumentException("Title must be between 1 and 200 characters.")
        }

        if (description.isEmpty() || description.length > 1000) {
            throw IllegalArgumentException("Description must be between 1 and 1000 characters.")
        }
    }

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
