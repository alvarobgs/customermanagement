package br.com.abg.core.configuration.exceptionhandler

import br.com.abg.core.common.RESPONSE_DATETIME_PATTERN
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RestExceptionResponse(
    @field:[JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RESPONSE_DATETIME_PATTERN)]
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val error: Error = Error()
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Error(
        val message: String? = null,
        val description: String? = null,
        val fields: MutableList<Field> = mutableListOf()
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class Field(
        val name: String? = null,
        val value: String? = null,
        val message: String? = null
    )
}
