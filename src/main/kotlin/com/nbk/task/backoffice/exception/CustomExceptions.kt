package com.nbk.task.backoffice.exception

// Password
class PasswordTooShortException : IllegalArgumentException("Password must be at least 8 characters long")
class PasswordMissingUppercaseException : IllegalArgumentException("Password must contain at least one uppercase letter")
class PasswordMissingDigitException : IllegalArgumentException("Password must contain at least one number")

// Username
class UsernameAlreadyExistsException : RuntimeException("Username already exists")