package com.umin.todoapp.domain.user.service

import com.umin.todoapp.domain.user.dto.*
import org.springframework.web.multipart.MultipartFile

interface UserService {

    fun signup(request: SignupRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse

    fun uploadImage(file: MultipartFile): ImageResponse
}