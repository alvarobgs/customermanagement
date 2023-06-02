package br.com.abg.customermanagementapp.infrastructure.adapter.repository.customer

import br.com.abg.customermanagementapp.domain.entity.customer.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByDocument(document: String): Customer?
}