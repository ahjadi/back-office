package com.nbk.task.backoffice.repository

import com.nbk.task.backoffice.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustomerRepository : JpaRepository<CustomerEntity, Long> {
}