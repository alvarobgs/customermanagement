package br.com.abg.domain.adapter.input.rest.controller.customer.model.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDate

@JsonNaming(SnakeCaseStrategy::class)
data class FindCustomerByDocumentResponse(
    val name: String,
    val document: String,
    val phone: String,
    val bornDate: LocalDate,
    val address: Address
) {
    data class Address(
        val zipCode: String,
        val number: String,
        val street: String,
        val neighborhood: String,
        val city: String,
        val state: String
    )
}