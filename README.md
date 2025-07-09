# Back Office Application

A Spring Boot REST API application built with Kotlin and Maven for managing customers with JWT-based authentication.

## Table of Contents
- [Overview](#overview)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Security Features](#security-features)
- [API Endpoints](#api-endpoints)
- [Data Transfer Objects (DTOs)](#data-transfer-objects-dtos)
- [Exception Handling](#exception-handling)
- [Database Schema](#database-schema)
- [Setup and Configuration](#setup-and-configuration)

## Overview

This back-office application provides a secure REST API for user authentication and customer management operations. It features JWT-based authentication, comprehensive error handling, and request/response logging.

## Technologies

- **Framework**: Spring Boot 3.5.3
- **Language**: Kotlin 1.9.25
- **Build Tool**: Maven
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT (jjwt 0.11.5)
- **Java Version**: 17

## Project Structure

```
src/main/kotlin/com/nbk/task/backoffice/
├── authentication/
│   ├── jwt/
│   │   ├── JwtAuthenticationFilter.kt
│   │   └── JwtService.kt
│   ├── AuthenticationController.kt
│   ├── CustomUserDetailsService.kt
│   ├── SecurityConfig.kt
│   └── TokenResponse.kt
├── controller/
│   └── CustomerController.kt
├── dto/
│   ├── CustomerDto.kt
│   └── UserDto.kt
├── entity/
│   ├── CustomerEntity.kt
│   └── UserEntity.kt
├── exception/
│   ├── CustomExceptions.kt
│   └── GlobalExceptionHandler.kt
├── repository/
│   ├── CustomerRepository.kt
│   └── UserRepository.kt
├── service/
│   ├── CustomerService.kt
│   └── UserService.kt
├── BackOfficeApplication.kt
└── LoggingFilter.kt
```

## Security Features

### JWT Authentication
- **Token Generation**: Uses HMAC SHA algorithm with configurable secret key
- **Token Expiration**: 24 hours (configurable)
- **Bearer Token**: Required in Authorization header for protected endpoints

### Security Configuration
- **Password Encryption**: BCrypt password encoder
- **Stateless Sessions**: No server-side session storage
- **CSRF Protection**: Disabled (suitable for stateless API)
- **Public Endpoints**: `/auth/**` (registration and login)
- **Protected Endpoints**: All other endpoints require authentication

### Authentication Flow
1. User registers with username and password
2. Password is validated and encrypted before storage
3. User logs in with credentials
4. Server returns JWT token upon successful authentication
5. Client includes token in Authorization header for subsequent requests
6. `JwtAuthenticationFilter` validates token for each protected request

### Password Validation Rules
- Minimum 8 characters length
- At least one uppercase letter
- At least one digit

## API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}

Response: 200 OK
{
  "message": "User with username: {username} created successfully"
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Customer Endpoints (Protected)

#### Add Customer
```http
POST /customers/add
Authorization: Bearer {token}
Content-Type: application/json

{
  "customerName": "string",
  "dateOfBirth": "yyyy-MM-dd",
  "gender": "M" | "F"
}

Response: 200 OK
{
  "customerId": 1,
  "customerName": "string",
  "customerNumber": 123456789,
  "dateOfBirth": "yyyy-MM-dd",
  "gender": "M" | "F"
}
```

#### Update Customer
```http
PATCH /customers/update/{customerId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "newCustomerName": "string",
  "newDateOfBirth": "yyyy-MM-dd",
  "newGender": "M" | "F"
}

Response: 200 OK
{
  "customerId": 1,
  "newCustomerName": "string",
  "customerNumber": 123456789,
  "newDateOfBirth": "yyyy-MM-dd",
  "newGender": "M" | "F"
}
```

#### Delete Customer
```http
DELETE /customers/delete/{customerId}
Authorization: Bearer {token}

Response: 200 OK
{
  "message": "Customer Deleted Successfully"
}
```

#### List All Customers
```http
GET /customers/list
Authorization: Bearer {token}

Response: 200 OK
{
  "customers": [
    {
      "id": 1,
      "customerNumber": 123456789,
      "customerName": "string",
      "dateOfBirth": "yyyy-MM-dd",
      "gender": "M" | "F"
    }
  ]
}
```

## Data Transfer Objects (DTOs)

### User DTOs

```kotlin
data class CreateUserRequest(
    val username: String,
    val password: String
)

data class CreateUserResponse(
    val message: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class TokenResponse(
    val token: String?
)
```

### Customer DTOs

```kotlin
data class AddCustomerRequest(
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender
)

data class AddCustomerResponse(
    val customerId: Long,
    val customerName: String,
    val customerNumber: Int,
    val dateOfBirth: LocalDate,
    val gender: Gender
)

data class UpdateCustomerRequest(
    val newCustomerName: String,
    val newDateOfBirth: LocalDate,
    val newGender: Gender
)

data class UpdateCustomerResponse(
    val customerId: Long,
    val newCustomerName: String,
    val customerNumber: Int,
    val newDateOfBirth: LocalDate,
    val newGender: Gender
)

data class Customer(
    val id: Long,
    val customerNumber: Int,
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender
)

data class ListOfCustomers(
    val customers: List<Customer>
)

data class GenericResponseMessage(
    val message: String
)
```

## Exception Handling

### Custom Exceptions

#### Password Validation Exceptions
- **PasswordTooShortException**: Password must be at least 8 characters long
- **PasswordMissingUppercaseException**: Password must contain at least one uppercase letter
- **PasswordMissingDigitException**: Password must contain at least one number

#### User Management Exceptions
- **UsernameAlreadyExistsException**: Username already exists
- **UsernameNotFoundException**: Invalid user credentials

#### Customer Management Exceptions
- **CustomerCreationException**: Customer could not be saved
- **CustomerUpdateException**: Customer could not be found/updated
- **CustomerDeleteException**: Customer could not be found/deleted

### Global Exception Handler

All exceptions are handled by `GlobalExceptionHandler` which returns standardized error responses:

```json
{
  "timestamp": "2025-01-09T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Specific error message"
}
```

HTTP Status Codes:
- `400 BAD_REQUEST`: Validation errors, illegal arguments
- `401 UNAUTHORIZED`: Authentication failures
- `409 CONFLICT`: Username already exists
- `500 INTERNAL_SERVER_ERROR`: Unexpected errors

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

### Customers Table
```sql
CREATE TABLE customers (
    id              SERIAL PRIMARY KEY,
    customer_number INTEGER      NOT NULL UNIQUE,
    customer_name   VARCHAR(255) NOT NULL,
    date_of_birth   DATE         NOT NULL,
    gender          VARCHAR(1)   NOT NULL
);
```

**Note**: Customer numbers are automatically generated as random 9-digit integers (100000000-999999999).

## Setup and Configuration

### Prerequisites
- Java 17
- Maven 3.9+
- PostgreSQL database

### Environment Variables
Configure the following environment variables:

```properties
PORT=8080
DBURL=jdbc:postgresql://localhost:5432/backoffice
USERNAME=your_db_username
PASSWORD=your_db_password
SECRET=your_jwt_secret_key
```

### Running the Application

1. Clone the repository
2. Set up PostgreSQL database and create the schema
3. Configure environment variables
4. Run the application:

```bash
./mvnw spring-boot:run
```

Or build and run the JAR:

```bash
./mvnw clean package
java -jar target/back-office-0.0.1-SNAPSHOT.jar
```

## Additional Features

### Request/Response Logging
The application includes a `LoggingFilter` that logs:
- All incoming requests (method, URI, body)
- All outgoing responses (status, body)
- Color-coded status codes for better visibility

### Entity Relationship Diagram
An ERD is available at `src/main/resources/erd.png` showing the database structure.

## License
This project is proprietary software. All rights reserved.