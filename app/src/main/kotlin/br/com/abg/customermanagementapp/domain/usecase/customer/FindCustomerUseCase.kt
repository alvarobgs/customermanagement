package br.com.abg.customermanagementapp.domain.usecase.customer

import br.com.abg.customermanagementapp.domain.entity.customer.Customer
import br.com.abg.customermanagementapp.domain.service.customer.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindCustomerUseCase(
    private val customerService: CustomerService
) {

    private val logger = LoggerFactory.getLogger(FindCustomerUseCase::class.java)
    fun execute(document: String): Customer? {
        logger.info("Fetching customer by document $document")

        return customerService.findByDocument(document)
    }
}