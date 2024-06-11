package com.example.kkang.api.dto

import com.example.kkang.api.domain.OrderStatus
import jakarta.validation.constraints.NotBlank

data class PostOrderRequestDto(
    @field:NotBlank(message = "userId를 입력하세요.")
    val userId: Long,
    @field:NotBlank(message = "상품을 입력하세요.")
    val product: String,
    val amount: Int = 1,
    val status: OrderStatus = OrderStatus.PENDING,
)
