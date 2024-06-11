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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getUser(): List<GetUserResponseDto> {
        return userService.getUsers()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): GetUserResponseDto {
        return userService.getUserById(id)
    }

    @PostMapping
    fun postUser(@Valid @RequestBody request: PostUserRequestDto): PostUserResponseDto {
        return userService.postUser(request)
    }
}