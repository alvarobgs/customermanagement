package br.com.abg.customermanagementapp.domain.usecase.customer

import br.com.abg.customermanagementapp.domain.entity.customer.Customer
import br.com.abg.customermanagementapp.domain.exceptions.InvalidArgumentException
import br.com.abg.customermanagementapp.infrastructure.adapter.repository.customer.CustomerRepository
import br.com.abg.customermanagementapp.infrastructure.common.Messages
import io.micrometer.core.instrument.Metrics
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreateCustomerUseCase(
    private val repository: CustomerRepository
) {

    private val logger = LoggerFactory.getLogger(CreateCustomerUseCase::class.java)
    private val metricName = "customer-management-new-customer"

    fun execute(customer: Customer): Customer {
        logger.info("Starting process to persist new customer with document ${customer.document}")

        customer.creationDate = LocalDateTime.now()

        return runCatching {
            repository.save(customer)
        }.onSuccess {
            Metrics.counter(metricName, "persistence_status", "success").increment()
            logger.info("Successfully saved customer with document ${customer.document}")
        }.onFailure {
            Metrics.counter(metricName, "persistence_status", "error").increment()
            logger.error("Failed to save customer with document ${customer.document}", it)
            if (it.cause is ConstraintViolationException) {
                throw InvalidArgumentException(Messages.findByKey("document.in.use.error"))
            }
        }.getOrThrow()
    }
}