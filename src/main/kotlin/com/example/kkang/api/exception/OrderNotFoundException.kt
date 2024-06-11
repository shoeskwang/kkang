package com.example.kkang.api.exception

class OrderNotFoundException(id: Long) : RuntimeException("Order not found with id: $id")