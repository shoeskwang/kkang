package com.example.kkang.api.dto

import com.example.kkang.api.domain.OrderStatus
import jakarta.validation.constraints.NotBlank

data class PatchOrderRequestDto(
    @field:NotBlank(message = "상태값을 입력하세요.")
    val status: OrderStatus,
)
