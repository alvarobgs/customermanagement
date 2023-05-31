package br.com.abg.domain.usecase.customer

import br.com.abg.domain.adapter.input.rest.controller.customer.model.request.CreateCustomerRequest
import br.com.abg.domain.adapter.output.kafka.producer.customer.CustomerProducer
import br.com.abg.domain.entity.customer.Customer
import br.com.abg.domain.service.customer.CustomerService
import br.com.abg.domain.service.zipcode.ZipCodeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindCustomerUseCase(
    private val customerService: CustomerService
) {

    private val logger = LoggerFactory.getLogger(FindCustomerUseCase::class.java)
    fun execute(document: String): Customer? {
        logger.info("Fetching customer by document $document")

        return try {
            customerService.findByDocument(document)
        } catch (e: Exception) { //FIXME alterar para capturar exception correta de not found
            logger.warn("Customer not found for document $document")
            null
        }
    }
}