package com.example.kkang.api.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val userId: Long,
    val product: String,
    val amount: Int,
    var status: OrderStatus
) {
    fun updateStatus(status: OrderStatus) {
        this.status = status
    }
}