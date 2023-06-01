package br.com.abg.domain.adapter.input.rest.controller.customer.model.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDate

@JsonNaming(SnakeCaseStrategy::class)
data class CreateCustomerRequest(
    val name: String,
    val document: String,
    val phone: String,
    val bornDate: LocalDate,
    val address: Address
) {
    data class Address(
        val zipCode: String,
        val number: String
    )
}
