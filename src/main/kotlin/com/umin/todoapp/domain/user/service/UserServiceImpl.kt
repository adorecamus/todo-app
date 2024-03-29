package com.umin.todoapp.domain.user.service

import com.umin.todoapp.domain.exception.InvalidCredentialException
import com.umin.todoapp.domain.exception.ModelNotFoundException
import com.umin.todoapp.domain.user.dto.*
import com.umin.todoapp.domain.user.model.User
import com.umin.todoapp.domain.user.repository.IUserRepository
import com.umin.todoapp.infra.s3.S3Service
import com.umin.todoapp.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserServiceImpl(
    private val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val s3Service: S3Service
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

    override fun login(request: LoginRequest): LoginResponse {

        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email
            )
        )
    }

    override fun uploadImage(file: MultipartFile): ImageResponse {
        return ImageResponse(
            url = s3Service.putObject(file, "user/")
        )
    }

}