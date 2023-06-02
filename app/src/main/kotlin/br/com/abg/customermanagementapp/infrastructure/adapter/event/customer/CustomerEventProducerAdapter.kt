package br.com.abg.customermanagementapp.infrastructure.adapter.event.customer

import br.com.abg.kafka.schema.customer.CustomerKafkaPayload

interface CustomerEventProducerAdapter {

    fun produce(payload: CustomerKafkaPayload)
}