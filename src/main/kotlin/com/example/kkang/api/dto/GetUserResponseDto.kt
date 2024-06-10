package com.example.kkang.api.dto

data class GetUserResponseDto (
    val id: Long,
    val name: String? = null,
    val email: String? = null,
)
