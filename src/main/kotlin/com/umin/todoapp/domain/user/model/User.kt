package com.umin.todoapp.domain.user.model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "name")
    val name: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}