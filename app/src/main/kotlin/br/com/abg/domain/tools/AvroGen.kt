package br.com.abg.domain.tools

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.avro.AvroFactory
import com.fasterxml.jackson.dataformat.avro.AvroSchema
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
class AvroGen {

    fun generate(): String {
        val mapper = ObjectMapper(AvroFactory())
        val gen = AvroSchemaGenerator()
        mapper.acceptJsonFormatVisitor(CustomerKafkaPayload::class.java, gen)
        val schemaWrapper: AvroSchema = gen.generatedSchema
        val avroSchema: org.apache.avro.Schema = schemaWrapper.avroSchema
        return avroSchema.toString(true)
    }
}