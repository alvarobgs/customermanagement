package br.com.abg.domain.adapter.output.kafka.producer.customer.impl

import br.com.abg.domain.adapter.output.kafka.producer.customer.CustomerProducer
import br.com.abg.kafka.schema.customer.CustomerKafkaPayload
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class CustomerProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, CustomerKafkaPayload>,
    @Value("\${kafka.topicname.newcustomer}") private val topicName: String,
    @Value("\${toggle.enable-kafka-publish:false}") private val enablePublishToggle: Boolean
) : CustomerProducer {

    private val logger = LoggerFactory.getLogger(CustomerProducerImpl::class.java)
    override fun produce(payload: CustomerKafkaPayload) {
        if (!enablePublishToggle) {
            logger.warn("Toggle disabled, skiping kafka ")
            return
        }
        logger.info("Starting process to produce message on kafka topic $topicName")

        runCatching {
            kafkaTemplate.send(topicName, payload).get()
        }.onSuccess {
            logger.info("Successfully produced message on kafka topic $topicName")
        }.onFailure {
           logger.error("Failed to produce message on kafka topic $topicName", it)
        }
    }
}