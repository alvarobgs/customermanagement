package br.com.abg.domain.adapter.output.kafka.producer.customer

interface CustomerProducer {

    fun produce(payload: String)
}