package com.nbk.task.backoffice.controller

import com.nbk.task.backoffice.dto.*
import com.nbk.task.backoffice.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(val customerService: CustomerService) {

    @PostMapping
    fun addCustomer(@RequestBody request: AddCustomerRequest): ResponseEntity<AddCustomerResponse?> {
        return ResponseEntity.ok(
            customerService.addCustomer(
                customerName = request.customerName,
                dateOfBirth = request.dateOfBirth,
                gender = request.gender
            )
        )
    }

    @PatchMapping("/{customerId}")
    fun updateCustomer(
        @RequestBody request: UpdateCustomerRequest,
        @PathVariable customerId: Long
    ): ResponseEntity<UpdateCustomerResponse?> {
        return ResponseEntity.ok(
            customerService.updateCustomer(
                customerId = customerId,
                customerName = request.newCustomerName,
                dateOfBirth = request.newDateOfBirth,
                gender = request.newGender
            )
        )
    }

    @DeleteMapping("/{customerId}")
    fun deleteCustomer(@PathVariable customerId: Long): ResponseEntity<GenericResponseMessage?> {
        return ResponseEntity.ok(customerService.deleteCustomer(customerId = customerId))
    }

    @GetMapping
    fun retrieveAllCustomers(): ResponseEntity<ListOfCustomers> {
        return ResponseEntity.ok(customerService.retrieveAllCustomers())
    }
}