package com.nbk.task.backoffice.dto

data class CreateUserRequest(val username: String, val password: String)

data class CreateUserResponse(val message: String)

data class LoginRequest(
    val username: String,
    val password: String
)