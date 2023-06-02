package br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response

import br.com.abg.customermanagementapp.infrastructure.common.API_DATETIME_PATTERN
import br.com.abg.customermanagementapp.infrastructure.common.TIMEZONE
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
