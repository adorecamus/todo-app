package com.umin.todoapp.domain.user.service

import com.umin.todoapp.domain.user.dto.SignupRequest
import com.umin.todoapp.domain.user.dto.UserResponse
import com.umin.todoapp.domain.user.model.User
import com.umin.todoapp.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun signup(request: SignupRequest): UserResponse {

        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            name = request.name
        )
            .let { userRepository.save(it) }
            .let { UserResponse.from(it) }
    }

}