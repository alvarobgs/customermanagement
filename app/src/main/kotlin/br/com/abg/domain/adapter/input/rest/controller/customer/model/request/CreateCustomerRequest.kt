package br.com.abg.domain.adapter.input.rest.controller.customer.model.request

import br.com.abg.core.common.API_DATE_PATTERN
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@JsonNaming(SnakeCaseStrategy::class)
data class CreateCustomerRequest(
    @field:NotBlank
    @field:Pattern(regexp = "[a-zA-Z]{3,100}")
    val name: String?,
    @field:NotBlank
    @field:Pattern(regexp = "[0-9]{11}")
    val document: String?,
    @field:NotBlank
    @field:Pattern(regexp = "[0-9]{2}-[0-9]{8,10}")
    val phone: String?,
    @field:NotNull
    @DateTimeFormat(pattern = API_DATE_PATTERN)
    val bornDate: LocalDate?,
    @field:Valid
    @field:NotNull
    val address: Address?
) {

    @JsonNaming(SnakeCaseStrategy::class)
    data class Address(
        @field:NotBlank
        @field:Pattern(regexp = "[0-9]{8}")
        val zipCode: String?,
        @field:NotBlank
        @field:Pattern(regexp = "[0-9]{1,10}")
        val number: String?
    )
}
