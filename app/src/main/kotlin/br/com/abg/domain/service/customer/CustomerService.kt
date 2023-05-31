package br.com.abg.domain.service.customer

import br.com.abg.domain.entity.customer.Customer

interface CustomerService {

    fun persist(customer: Customer): Customer

    fun findByDocument(document: String): Customer
}