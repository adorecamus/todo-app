package com.umin.todoapp.domain.user.controller

import com.umin.todoapp.domain.user.dto.SignupRequest
import com.umin.todoapp.domain.user.dto.UserResponse
import com.umin.todoapp.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signup(request))
    }

}