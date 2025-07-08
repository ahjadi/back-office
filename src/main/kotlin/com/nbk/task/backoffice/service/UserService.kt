package com.nbk.task.backoffice.service

import com.nbk.task.backoffice.dto.CreateUserResponse
import com.nbk.task.backoffice.entity.UserEntity
import com.nbk.task.backoffice.exception.PasswordMissingDigitException
import com.nbk.task.backoffice.exception.PasswordMissingUppercaseException
import com.nbk.task.backoffice.exception.PasswordTooShortException
import com.nbk.task.backoffice.exception.UsernameAlreadyExistsException
import com.nbk.task.backoffice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun create(username: String, password: String): CreateUserResponse {
        if (userRepository.existsByUsername(username)) {
            throw UsernameAlreadyExistsException() as Throwable
        }

        validatePassword(password)

        val hashedPassword = passwordEncoder.encode(password)
        val newUser = UserEntity(username = username, password = hashedPassword)

        val savedUser = userRepository.save(newUser)
        return CreateUserResponse("User with username: ${savedUser.username} created successfully")
    }

    fun validatePassword(password: String) {
        if (password.length < 8) {
            throw PasswordTooShortException() as Throwable
        }
        if (!password.any { it.isUpperCase() }) {
            throw PasswordMissingUppercaseException() as Throwable
        }
        if (!password.any { it.isDigit() }) {
            throw PasswordMissingDigitException() as Throwable
        }
    }

}