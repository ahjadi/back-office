package com.nbk.task.backoffice.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "customers")
data class CustomerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val customerNumber: Int = (100_000_000..999_999_999).random(),
    val customerName: String,
    val dateOfBirth: LocalDate,

    @Enumerated(EnumType.STRING)
    val gender: Gender,

    ) {
    constructor() : this(
        id = null,
        customerNumber = 0,
        customerName = "",
        dateOfBirth = LocalDate.now(),
        gender = Gender.M
    )

}

enum class Gender {
    M, F
}