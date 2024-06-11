package com.example.kkang.api.controller

import com.example.kkang.api.domain.Order
import com.example.kkang.api.dto.GetUserResponseDto
import com.example.kkang.api.dto.PatchOrderRequestDto
import com.example.kkang.api.dto.PostOrderRequestDto
import com.example.kkang.api.dto.PostUserRequestDto
import com.example.kkang.api.dto.PostUserResponseDto
import com.example.kkang.api.service.OrderService
import com.example.kkang.api.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): List<Order> {
        return orderService.getOrdersByUserId(userId)
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): Order {
        return orderService.getOrderById(id)
    }

    @PostMapping
    fun postOrder(@RequestBody request: PostOrderRequestDto): Order {
        return orderService.postOrder(request)
    }

    @PatchMapping("/{id}")
    fun updateOrder(@PathVariable id: Long, @RequestBody request: PatchOrderRequestDto): Order {
        return orderService.patchOrder(id, request)
    }
}