package com.nbk.task.backoffice.service

import com.nbk.task.backoffice.dto.*
import com.nbk.task.backoffice.entity.CustomerEntity
import com.nbk.task.backoffice.entity.Gender
import com.nbk.task.backoffice.exception.CustomerCreationException
import com.nbk.task.backoffice.exception.CustomerDeleteException
import com.nbk.task.backoffice.exception.CustomerUpdateException
import com.nbk.task.backoffice.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun addCustomer(customerName: String, dateOfBirth: LocalDate, gender: Gender): AddCustomerResponse {
        val newCustomer = CustomerEntity(customerName = customerName, dateOfBirth = dateOfBirth, gender = gender)
        val savedCustomer = customerRepository.save(newCustomer)

        if (savedCustomer.id == null) {
            throw CustomerCreationException("Customer could not be saved")
        }
        return AddCustomerResponse(
            customerId = savedCustomer.id!!,
            customerName = savedCustomer.customerName,
            customerNumber = savedCustomer.customerNumber,
            dateOfBirth = savedCustomer.dateOfBirth,
            gender = savedCustomer.gender
        )
    }

    fun updateCustomer(
        customerId: Long,
        customerName: String,
        dateOfBirth: LocalDate,
        gender: Gender
    ): UpdateCustomerResponse {
        val existingCustomer = customerRepository.findById(customerId).orElseThrow {
            CustomerUpdateException("Customer could not be found")
        }

        val updatedCustomer =
            existingCustomer.copy(customerName = customerName, dateOfBirth = dateOfBirth, gender = gender)
        val savedCustomer = customerRepository.save(updatedCustomer)

        if (savedCustomer.id == null) {
            throw CustomerUpdateException("Customer could not be updated")
        }
        return UpdateCustomerResponse(
            customerId = savedCustomer.id!!,
            newCustomerName = savedCustomer.customerName,
            customerNumber = savedCustomer.customerNumber,
            newDateOfBirth = savedCustomer.dateOfBirth,
            newGender = savedCustomer.gender
        )
    }

    fun deleteCustomer(customerId: Long): GenericResponseMessage {
        val customer: CustomerEntity = customerRepository.findById(customerId)
            .orElseThrow { CustomerDeleteException("Customer could not be found") }

        customerRepository.delete(customer)

        if (customerRepository.existsById(customerId)) {
            throw CustomerDeleteException("Failed to delete customer with ID $customerId")
        }

        return GenericResponseMessage("Customer Deleted Successfully")
    }

    fun retrieveAllCustomers(): ListOfCustomers {
        val customers = customerRepository.findAll()
            .map { entity ->
                Customer(
                    id = entity.id!!,
                    customerNumber = entity.customerNumber,
                    customerName = entity.customerName,
                    dateOfBirth = entity.dateOfBirth,
                    gender = entity.gender
                )
            }

        return ListOfCustomers(customers)
    }

}