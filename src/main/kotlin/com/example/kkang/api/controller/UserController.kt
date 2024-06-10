package com.example.kkang.api.controller

import com.example.kkang.api.dto.GetUserResponseDto
import com.example.kkang.api.dto.PostUserRequestDto
import com.example.kkang.api.dto.PostUserResponseDto
import com.example.kkang.api.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users")
    fun getUser(): List<GetUserResponseDto> {
        return userService.getUsers()
    }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id: Long): GetUserResponseDto {
        return userService.getUserById(id)
    }

    @PostMapping("/users")
    fun postUser(@Valid @RequestBody request: PostUserRequestDto): PostUserResponseDto {
        return userService.postUser(request)
    }
}