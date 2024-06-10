package com.example.kkang.api.service

import com.example.kkang.api.domain.User
import com.example.kkang.api.dto.GetUserResponseDto
import com.example.kkang.api.dto.PostUserRequestDto
import com.example.kkang.api.dto.PostUserResponseDto
import com.example.kkang.api.exception.UserNotFoundException
import com.example.kkang.api.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getUsers(): List<GetUserResponseDto> {
        val users = userRepository.findAll()
        return users.map { user ->
            GetUserResponseDto(
                id = user.id,
                name = user.name,
                email = user.email,
            )
        }
    }

    fun getUserById(id: Long): GetUserResponseDto {
        val user = userRepository.findByIdOrNull(id) ?: throw UserNotFoundException(id)
        return GetUserResponseDto(
            id = user.id,
            name = user.name,
            email = user.email,
        )
    }

    fun postUser(request: PostUserRequestDto): PostUserResponseDto {
        val user = userRepository.save(
            User(
                name = request.name,
                email = request.email,
            )
        )

        return PostUserResponseDto(
            id = user.id,
            name = user.name,
            email = user.email,
        )
    }
}