package com.umin.todoapp.domain.user.service

import com.umin.todoapp.domain.user.dto.LoginRequest
import com.umin.todoapp.domain.user.dto.LoginResponse
import com.umin.todoapp.domain.user.dto.SignupRequest
import com.umin.todoapp.domain.user.dto.UserResponse

interface UserService {

    fun signup(request: SignupRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse
}