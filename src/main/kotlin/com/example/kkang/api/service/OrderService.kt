package com.example.kkang.api.service

import com.example.kkang.api.domain.Order
import com.example.kkang.api.dto.PatchOrderRequestDto
import com.example.kkang.api.dto.PostOrderRequestDto
import com.example.kkang.api.exception.OrderNotFoundException
import com.example.kkang.api.exception.UserNotFoundException
import com.example.kkang.api.repository.OrderRepository
import com.example.kkang.api.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) {
    fun getOrdersByUserId(userId: Long): List<Order> {
        return orderRepository.findByUserId(userId)
    }

    fun getOrderById(id: Long): Order {
        return orderRepository.findByIdOrNull(id) ?: throw OrderNotFoundException(id)
    }

    fun postOrder(request: PostOrderRequestDto): Order {
        validateUser(request)
        val order = Order(
            userId = request.userId,
            product = request.product,
            amount = request.amount,
            status = request.status
        )
        return orderRepository.save(order)
    }

    private fun validateUser(request: PostOrderRequestDto) {
        userRepository.findByIdOrNull(request.userId) ?: throw UserNotFoundException(request.userId)
    }

    fun patchOrder(id: Long, request: PatchOrderRequestDto): Order {
        val order = orderRepository.findByIdOrNull(id) ?: throw OrderNotFoundException(id)
        order.updateStatus(request.status)
        return orderRepository.save(order)
    }
}