package com.nbk.task.backoffice.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.nbk.task.backoffice.entity.Gender
import java.time.LocalDate

data class AddCustomerRequest(
    val customerName: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

data class GenericResponseMessage(val message: String)

data class Customer(
    val id: Long,
    val customerNumber: Int,
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
)

data class ListOfCustomers(
    val customers: List<Customer>
)