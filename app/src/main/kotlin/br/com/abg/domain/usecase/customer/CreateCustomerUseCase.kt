package br.com.abg.domain.usecase.customer

import br.com.abg.domain.adapter.input.rest.controller.customer.model.request.CreateCustomerRequest
import br.com.abg.domain.adapter.input.rest.mapper.customer.mapToCustomer
import br.com.abg.domain.adapter.input.rest.mapper.customer.mapToCustomerKafkaPayload
import br.com.abg.domain.adapter.output.kafka.producer.customer.CustomerProducer
import br.com.abg.domain.entity.customer.Customer
import br.com.abg.domain.service.customer.CustomerService
import br.com.abg.domain.service.zipcode.ZipCodeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateCustomerUseCase(
    private val customerService: CustomerService,
    private val zipCodeService: ZipCodeService,
    private val customerProducer: CustomerProducer
) {

    private val logger = LoggerFactory.getLogger(CreateCustomerUseCase::class.java)
    fun execute(customer: CreateCustomerRequest): Customer {
        logger.info("Starting process to create new customer")

        val completeAddress = zipCodeService.fetchAddressByZipCode(customer.address.zipCode)

        return customerService.persist(customer.mapToCustomer(completeAddress)).also {
            customerProducer.produce(it.mapToCustomerKafkaPayload())
        }
    }
}