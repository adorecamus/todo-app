package com.umin.todoapp.domain.comment.controller

import com.umin.todoapp.domain.comment.dto.CommentRequest
import com.umin.todoapp.domain.comment.dto.CommentResponse
import com.umin.todoapp.domain.comment.dto.DeleteCommentRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/todos/{todoId}/comments")
@RestController
class CommentController {

    @PostMapping
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        TODO()
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentRequest
    ): ResponseEntity<CommentResponse> {
        TODO()
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: DeleteCommentRequest
    ): ResponseEntity<CommentResponse> {
        TODO()
    }

}