package com.example.kkang.api.repository

import com.example.kkang.api.domain.Order
import com.example.kkang.api.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long>{
    fun findByUserId(userId: Long): List<Order>
}