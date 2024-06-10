package com.example.kkang.api.exception

class UserNotFoundException(id: Long) : RuntimeException("User not found with id $id")