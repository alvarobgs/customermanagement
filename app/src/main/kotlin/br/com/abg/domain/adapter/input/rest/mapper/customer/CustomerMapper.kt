package br.com.abg.domain.adapter.input.rest.mapper.customer

import br.com.abg.domain.adapter.input.rest.controller.customer.model.request.CreateCustomerRequest
import br.com.abg.domain.adapter.input.rest.controller.customer.model.response.CreateCustomerResponse
import br.com.abg.domain.adapter.input.rest.controller.customer.model.response.FindCustomerByDocumentResponse
import br.com.abg.domain.adapter.output.rest.client.zipcodeaddress.model.AddressByZipCodeResponse
import br.com.abg.domain.entity.customer.Address
import br.com.abg.domain.entity.customer.Customer
import br.com.abg.kafka.schema.customer.CustomerKafkaPayload

fun Customer.mapToFindCustomerByDocumentResponse() = FindCustomerByDocumentResponse(
    name = name!!,
    document = document!!,
    bornDate = bornDate!!,
    phone = phone!!,
    address = FindCustomerByDocumentResponse.Address(
        zipCode = address!!.zipCode!!,
        street = address!!.street!!,
        number = address!!.number!!,
        neighborhood = address!!.neighborhood!!,
        city = address!!.city!!,
        state = address!!.state!!
    )
)

fun Customer.mapToCreateCustomerResponse() = CreateCustomerResponse(
    id = id!!
)

fun CreateCustomerRequest.mapToCustomer(addressByZipCode: AddressByZipCodeResponse) : Customer{
    val addr = Address()
    addr.zipCode = address.zipCode
    addr.number = address.number
    addr.street = addressByZipCode.logradouro
    addr.neighborhood = addressByZipCode.bairro
    addr.city = addressByZipCode.localidade
    addr.state = addressByZipCode.uf

    val customer = Customer()
    customer.name = name
    customer.bornDate = bornDate
    customer.phone = phone
    customer.document = document
    customer.address = addr

    return customer
}

fun Customer.mapToCustomerKafkaPayload() : CustomerKafkaPayload {
    val addr = br.com.abg.kafka.schema.customer.`CustomerKafkaPayload$`.Address()

    addr.zipCode = address!!.zipCode
    addr.number = address!!.number
    addr.street = address!!.street
    addr.neighborhood = address!!.neighborhood
    addr.city = address!!.city
    addr.state = address!!.state

    val payload = CustomerKafkaPayload()
    payload.name = name
    payload.bornDate = bornDate.toString() //FIXME criar um conversor
    payload.phone = phone
    payload.document = document
    payload.address = addr

    return payload
}