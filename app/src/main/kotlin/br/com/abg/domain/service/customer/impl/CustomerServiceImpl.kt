package br.com.abg.domain.service.customer.impl

import br.com.abg.domain.entity.customer.Customer
import br.com.abg.domain.repository.customer.CustomerRepository
import br.com.abg.domain.service.customer.CustomerService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val repository: CustomerRepository
) : CustomerService {

    private val logger = LoggerFactory.getLogger(CustomerServiceImpl::class.java)
    override fun persist(customer: Customer): Customer {
        return runCatching {
            repository.save(customer)
        }.onSuccess {
            logger.info("Successfully saved customer with document ${customer.document}")
        }.onFailure {
            logger.error("Failed to save customer with document ${customer.document}")
        }.getOrThrow()
    }

    override fun findByDocument(document: String): Customer {
        TODO("Not yet implemented")
    }
}