package br.com.abg.customermanagementapp.domain.adapter.output.kafka.producer.customer

import br.com.abg.kafka.schema.customer.CustomerKafkaPayload

interface CustomerProducer {

    fun produce(payload: CustomerKafkaPayload)
}