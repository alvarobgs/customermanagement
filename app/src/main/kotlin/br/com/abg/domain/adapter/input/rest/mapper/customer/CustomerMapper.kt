package br.com.abg.domain.adapter.input.rest.mapper.customer

import br.com.abg.domain.adapter.input.rest.controller.customer.model.request.CreateCustomerRequest
import br.com.abg.domain.adapter.input.rest.controller.customer.model.response.CreateCustomerResponse
import br.com.abg.domain.adapter.input.rest.controller.customer.model.response.FindCustomerByDocumentResponse
import br.com.abg.domain.adapter.output.rest.client.zipcodeaddress.model.AddressByZipCodeResponse
import br.com.abg.domain.entity.customer.Customer

fun Customer.mapToFindCustomerByDocumentResponse() = FindCustomerByDocumentResponse(
    name = name!!,
    document = document!!,
    bornDate = bornDate!!,
    phone = phone!!,
    address = FindCustomerByDocumentResponse.Address(
        zipCode = address!!.zipCode!!,
        street = address.street!!,
        number = address.number!!,
        neighborhood = address.neighborhood!!,
        city = address.city!!,
        state = address.state!!
    )
)

fun Customer.mapToCreateCustomerResponse() = CreateCustomerResponse(
    id = id!!
)

fun CreateCustomerRequest.mapToCustomer(address: AddressByZipCodeResponse) = Customer(

)