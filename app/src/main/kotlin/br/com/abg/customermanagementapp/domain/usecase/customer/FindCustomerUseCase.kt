package br.com.abg.customermanagementapp.domain.usecase.customer

import br.com.abg.customermanagementapp.domain.entity.customer.Customer
import br.com.abg.customermanagementapp.infrastructure.adapter.repository.customer.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindCustomerUseCase(
    private val repository: CustomerRepository
) {

    private val logger = LoggerFactory.getLogger(FindCustomerUseCase::class.java)
    fun execute(document: String): Customer? {
        logger.info("Fetching customer by document $document")

        return repository.findByDocument(document)
    }
}