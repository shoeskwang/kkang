package com.example.kkang.api.service

import com.example.kkang.api.domain.Order
import com.example.kkang.api.domain.OrderStatus
import com.example.kkang.api.domain.User
import com.example.kkang.api.dto.PatchOrderRequestDto
import com.example.kkang.api.dto.PostOrderRequestDto
import com.example.kkang.api.exception.OrderNotFoundException
import com.example.kkang.api.exception.UserNotFoundException
import com.example.kkang.api.repository.OrderRepository
import com.example.kkang.api.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class OrderServiceTest {
    private lateinit var orderService: OrderService
    private lateinit var orderRepository: OrderRepository
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        orderRepository = mockk()
        userRepository = mockk()
        orderService = OrderService(orderRepository, userRepository)
    }

    @Test
    fun `user의 order list를 조회하면, order list가 반환된다`() {
        val usersOrder = listOf(
            Order(1L, 1L, "product1", 1, OrderStatus.COMPLETED),
            Order(2L, 1L, "product2", 3, OrderStatus.PROCESSING),
        )
        every { orderRepository.findByUserId(1L) } returns usersOrder

        val result = orderService.getOrdersByUserId(1L)

        assertEquals(2, result.size)
        assertEquals("product1", result[0].product)
        assertEquals("product2", result[1].product)
    }

    @Test
    fun `특정 order id를 조회하면, 해당 order가 반환된다`() {
        val order = Order(1L, 1L, "product1", 1, OrderStatus.COMPLETED)
        every { orderRepository.findByIdOrNull(1L) } returns order

        val result = orderService.getOrderById(1L)

        assertNotNull(result)
        assertEquals("product1", result.product)
        assertEquals(OrderStatus.COMPLETED, result.status)
    }

    @Test
    fun `존재하지 않는 user id로 order를 생성하면, UserNotFoundException 예외가 발생한다`() {
        val invalidOrderRequest = PostOrderRequestDto(99L, "product3", 3, OrderStatus.PENDING)
        every { userRepository.findByIdOrNull(invalidOrderRequest.userId) } throws UserNotFoundException(invalidOrderRequest.userId)

        val exception = assertThrows(UserNotFoundException::class.java) {
            orderService.postOrder(invalidOrderRequest)
        }

        assertEquals("User not found with id: ${invalidOrderRequest.userId}", exception.message)
    }

    @Test
    fun `정상적인 user id로 order를 생성하면, 생성된 order가 반환된다`() {
        val validOrderRequest = PostOrderRequestDto(1L, "product4", 1, OrderStatus.PENDING)
        val validOrder = Order(4L, 1L, "product4", 1, OrderStatus.PENDING)
        val user = User(id = 1, name = "Kim", email = "kim@test.com")
        every { userRepository.findByIdOrNull(validOrderRequest.userId) } returns user
        every { orderRepository.save(any<Order>()) } returns validOrder

        val result = orderService.postOrder(validOrderRequest)

        assertNotNull(result)
        assertEquals(4L, result.id)
        assertEquals("product4", result.product)
    }

    @Test
    fun `존재하지 않는 order id로 order를 수정하면, OrderNotFoundException 예외가 발생한다`() {
        val invalidOrderId = 99L
        val orderPatchRequest = PatchOrderRequestDto(OrderStatus.CANCELLED)
        every { orderRepository.findByIdOrNull(invalidOrderId) } throws OrderNotFoundException(invalidOrderId)

        val exception = assertThrows(OrderNotFoundException::class.java) {
            orderService.patchOrder(invalidOrderId, orderPatchRequest)
        }

        assertEquals("Order not found with id: $invalidOrderId", exception.message)
    }

    @Test
    fun `정상적인 order id로 order를 수정하면, 수정된 order가 반환된다`() {
        val validOrderId = 4L
        val orderPatchRequest = PatchOrderRequestDto(OrderStatus.COMPLETED)
        val validOrder = Order(4L, 1L, "product4", 1, OrderStatus.PENDING)
        every { orderRepository.findByIdOrNull(validOrderId) } returns validOrder
        every { orderRepository.save(any<Order>()) } returns validOrder.copy(status = orderPatchRequest.status)

        val result = orderService.patchOrder(validOrderId, orderPatchRequest)

        assertNotNull(result)
        assertEquals(4L, result.id)
        assertEquals("product4", result.product)
        assertEquals(OrderStatus.COMPLETED, result.status)
    }
}