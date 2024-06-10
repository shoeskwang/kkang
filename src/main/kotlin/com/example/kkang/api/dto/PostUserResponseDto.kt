package com.example.kkang.api.dto

data class PostUserResponseDto (
    val id: Long,
    val name: String? = null,
    val email: String? = null,
)
