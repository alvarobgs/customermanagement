package br.com.abg.domain.repository.customer

import br.com.abg.domain.entity.customer.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {

    fun findByDocument(document: String): Customer?
}