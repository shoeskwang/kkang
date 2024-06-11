package com.example.kkang.api.service

import com.example.kkang.api.domain.User
import com.example.kkang.api.dto.PostUserRequestDto
import com.example.kkang.api.exception.UserNotFoundException
import com.example.kkang.api.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class UserServiceTest {
    private lateinit var userService: UserService
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        userService = UserService(userRepository)
    }

    @Test
    fun `user list를 조회하면, user list가 반환된다`() {
        val users = listOf(
            User(id = 1, name = "Kim", email = "kim@test.com"),
            User(id = 2, name = "Lee", email = "lee@test.com")
        )
        every { userRepository.findAll() } returns users

        val result = userService.getUsers()

        assertEquals(2, result.size)
        assertEquals("Kim", result[0].name)
        assertEquals("Lee", result[1].name)
    }

    @Test
    fun `특정 user id를 조회하면, 해당 user가 반환된다`() {
        val userId = 1L
        val user = User(id = 1, name = "Kim", email = "kim@test.com")
        every { userRepository.findByIdOrNull(userId) } returns user

        val result = userService.getUserById(userId)

        assertNotNull(result)
        assertEquals("Kim", result.name)
        assertEquals("kim@test.com", result.email)
    }

    @Test
    fun `존재하지 않는 user id를 조회하면, UserNotFoundException 예외가 발생한다`() {
        val notFoundUserId = 11L
        every { userRepository.findByIdOrNull(notFoundUserId) } returns null

        val exception = assertThrows(UserNotFoundException::class.java) {
            userService.getUserById(notFoundUserId)
        }

        assertEquals("User not found with id: $notFoundUserId", exception.message)
    }

    @Test
    fun `user를 생성하면, 생성된 user가 반환된다`() {
        val request = PostUserRequestDto(name = "Kim", email = "kim@test.com")
        val user = User(id = 1, name = "Kim", email = "kim@test.com")
        every { userRepository.save(any<User>()) } returns user

        val result = userService.postUser(request)

        assertNotNull(result)
        assertEquals(1, result.id)
        assertEquals("Kim", result.name)
        assertEquals("kim@test.com", result.email)
    }
}