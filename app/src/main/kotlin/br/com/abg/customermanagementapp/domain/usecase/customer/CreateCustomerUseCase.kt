package br.com.abg.customermanagementapp.domain.usecase.customer

import br.com.abg.customermanagementapp.core.common.Messages
import br.com.abg.customermanagementapp.domain.adapter.input.rest.controller.customer.model.request.CreateCustomerRequest
import br.com.abg.customermanagementapp.domain.adapter.input.rest.mapper.customer.mapToCustomer
import br.com.abg.customermanagementapp.domain.adapter.input.rest.mapper.customer.mapToCustomerKafkaPayload
import br.com.abg.customermanagementapp.domain.adapter.output.kafka.producer.customer.CustomerProducer
import br.com.abg.customermanagementapp.domain.entity.customer.Customer
import br.com.abg.customermanagementapp.domain.exceptions.InvalidArgumentException
import br.com.abg.customermanagementapp.domain.service.customer.CustomerService
import br.com.abg.customermanagementapp.domain.service.zipcode.ZipCodeService
import org.hibernate.exception.ConstraintViolationException
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

        val completeAddress = zipCodeService.fetchAddressByZipCode(customer.address!!.zipCode!!)

        return runCatching {
            customerService.persist(customer.mapToCustomer(completeAddress))
        }.onSuccess {
            customerProducer.produce(it.mapToCustomerKafkaPayload())
        }.onFailure {
            if (it.cause is ConstraintViolationException) {
                throw InvalidArgumentException(Messages.findByKey("document.in.use.error", Messages.Language.PT))
            }
            logger.error("Failed to save customer", it)
        }.getOrThrow()
    }
}