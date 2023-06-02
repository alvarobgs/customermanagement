package br.com.abg.customermanagementapp.domain.service.customer.impl

import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.request.CreateCustomerRequest
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response.CreateCustomerResponse
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response.FindCustomerByDocumentResponse
import br.com.abg.customermanagementapp.domain.mapper.customer.mapToCreateCustomerResponse
import br.com.abg.customermanagementapp.domain.mapper.customer.mapToCustomer
import br.com.abg.customermanagementapp.domain.mapper.customer.mapToCustomerKafkaPayload
import br.com.abg.customermanagementapp.domain.mapper.customer.mapToFindCustomerByDocumentResponse
import br.com.abg.customermanagementapp.domain.service.customer.CustomerService
import br.com.abg.customermanagementapp.domain.service.zipcode.ZipCodeService
import br.com.abg.customermanagementapp.domain.usecase.customer.CreateCustomerUseCase
import br.com.abg.customermanagementapp.domain.usecase.customer.FindCustomerUseCase
import br.com.abg.customermanagementapp.infrastructure.adapter.event.customer.CustomerEventProducerAdapter
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val findCustomerUseCase: FindCustomerUseCase,
    private val customerProducer: CustomerEventProducerAdapter,
    private val zipCodeService: ZipCodeService
) : CustomerService {
    override fun createNewCustomer(request: CreateCustomerRequest): CreateCustomerResponse {
        val completeAddress = zipCodeService.fetchAddressByZipCode(request.address!!.zipCode!!)

        val customer = createCustomerUseCase.execute(request.mapToCustomer(completeAddress))
        customerProducer.produce(customer.mapToCustomerKafkaPayload())
        return customer.mapToCreateCustomerResponse()
    }

    override fun findByDocument(document: String): FindCustomerByDocumentResponse? {
        return findCustomerUseCase.execute(document)?.mapToFindCustomerByDocumentResponse()
    }
}