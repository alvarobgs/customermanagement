package br.com.abg.customermanagementapp.core.configuration.log

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.Logbook
import org.zalando.logbook.core.Conditions

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
            .build()
    }
}