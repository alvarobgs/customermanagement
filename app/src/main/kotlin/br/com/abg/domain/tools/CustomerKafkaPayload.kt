package br.com.abg.domain.tools

data class CustomerKafkaPayload(
    val name: String,
    val document: String,
    val phone: String,
    val bornDate: String,
    val address: Address
) {
    data class Address(
        val zipCode: String,
        val number: String,
        val street: String,
        val neighborhood: String,
        val city: String,
        val state: String
    )
}
