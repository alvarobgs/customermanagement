package br.com.abg.customermanagementapp.core.configuration.exceptionhandler

import br.com.abg.customermanagementapp.core.common.API_DATETIME_PATTERN
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
data class RestExceptionResponse(
    @field:[JsonFormat(shape = JsonFormat.Shape.STRING, pattern = API_DATETIME_PATTERN)]
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val error: Error = Error()
) {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Error(
        val message: String? = null,
        val description: String? = null,
        var fields: MutableList<Field>? = null
    )
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class Field(
        val name: String? = null,
        val value: String? = null,
        val message: String? = null
    )
}
