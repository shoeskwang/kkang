package com.example.kkang.api.dto

import jakarta.validation.constraints.NotBlank

data class PostUserRequestDto (
    @field:NotBlank(message = "이름을 입력하세요.")
    val name: String?,
    @field:NotBlank(message = "이메일을 입력하세요.")
    val email: String?,
)
