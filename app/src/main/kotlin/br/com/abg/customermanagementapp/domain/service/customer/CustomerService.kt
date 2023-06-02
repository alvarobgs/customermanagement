package br.com.abg.customermanagementapp.domain.service.customer

import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.request.CreateCustomerRequest
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response.CreateCustomerResponse
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response.FindCustomerByDocumentResponse

interface CustomerService {

    fun createNewCustomer(request: CreateCustomerRequest): CreateCustomerResponse

    fun findByDocument(document: String): FindCustomerByDocumentResponse?
}