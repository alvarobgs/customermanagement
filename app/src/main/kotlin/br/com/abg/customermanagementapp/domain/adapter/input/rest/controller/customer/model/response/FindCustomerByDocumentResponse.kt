package br.com.abg.customermanagementapp.domain.adapter.input.rest.controller.customer.model.response

import br.com.abg.customermanagementapp.core.common.API_DATETIME_PATTERN
import br.com.abg.customermanagementapp.core.common.API_DATE_PATTERN
import br.com.abg.customermanagementapp.core.common.TIMEZONE
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDate
import java.time.LocalDateTime

@JsonNaming(SnakeCaseStrategy::class)
data class FindCustomerByDocumentResponse(
    val name: String,
    val document: String,
    val phone: String,
    @field:JsonFormat(pattern = API_DATE_PATTERN, timezone = TIMEZONE)
    val bornDate: LocalDate,
    @field:JsonFormat(pattern = API_DATETIME_PATTERN, timezone = TIMEZONE)
    val creationDate: LocalDateTime,
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