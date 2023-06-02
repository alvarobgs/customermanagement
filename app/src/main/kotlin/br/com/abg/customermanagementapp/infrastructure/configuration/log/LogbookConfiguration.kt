package br.com.abg.customermanagementapp.infrastructure.configuration.log

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.Logbook
import org.zalando.logbook.core.Conditions
import org.zalando.logbook.json.JsonPathBodyFilters

@Configuration
class LogbookConfiguration {

    private val secret = "***"

    @Bean
    fun getLogbook(): Logbook? {
        return Logbook.builder()
            .condition(
                Conditions.exclude(
                    Conditions.requestTo("/actuator/**")
                )
            )
            .bodyFilter(JsonPathBodyFilters.jsonPath("$..name").replace(secret))
            .bodyFilter(JsonPathBodyFilters.jsonPath("$..phone").replace(secret))
            .build()
    }
}