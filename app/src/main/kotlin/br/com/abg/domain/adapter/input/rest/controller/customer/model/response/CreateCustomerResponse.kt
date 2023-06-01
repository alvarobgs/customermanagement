package br.com.abg.domain.adapter.input.rest.controller.customer.model.response

import br.com.abg.core.common.API_DATETIME_PATTERN
import br.com.abg.core.common.TIMEZONE
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(SnakeCaseStrategy::class)
data class CreateCustomerResponse(
    val id: Long,
    @field:JsonFormat(pattern = API_DATETIME_PATTERN, timezone = TIMEZONE)
    val creationDate: LocalDateTime
)
