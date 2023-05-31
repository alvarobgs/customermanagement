package br.com.abg.domain.adapter.input.rest.controller.customer.model.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(SnakeCaseStrategy::class)
data class CreateCustomerResponse(
    val id: Long
)
