package br.com.abg.domain.service.customer.impl

import br.com.abg.domain.entity.customer.Customer
import br.com.abg.domain.repository.customer.CustomerRepository
import br.com.abg.domain.service.customer.CustomerService
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Metrics
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CustomerServiceImpl(
    private val repository: CustomerRepository
) : CustomerService {

    private val metricName = "customer-management-new-customer"
    private val logger = LoggerFactory.getLogger(CustomerServiceImpl::class.java)
    override fun persist(customer: Customer): Customer {
        customer.creationDate = LocalDateTime.now()

        return runCatching {
            repository.save(customer)
        }.onSuccess {
            Metrics.counter(metricName, "persistence_status", "success").increment()
            logger.info("Successfully saved customer with document ${customer.document}")
        }.onFailure {
            Metrics.counter(metricName, "persistence_status", "error").increment()
            logger.error("Failed to save customer with document ${customer.document}")
        }.getOrThrow()
    }

    override fun findByDocument(document: String): Customer? {
        return repository.findByDocument(document)
    }
}