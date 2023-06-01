package br.com.abg.domain.adapter.output.kafka.producer.customer

import br.com.abg.kafka.schema.customer.CustomerKafkaPayload

interface CustomerProducer {

    fun produce(payload: CustomerKafkaPayload)
}