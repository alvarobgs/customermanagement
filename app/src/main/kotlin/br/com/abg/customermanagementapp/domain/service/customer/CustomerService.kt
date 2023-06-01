package br.com.abg.customermanagementapp.domain.service.customer

import br.com.abg.customermanagementapp.domain.entity.customer.Customer

interface CustomerService {

    fun persist(customer: Customer): Customer

    fun findByDocument(document: String): Customer?
}