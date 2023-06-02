package br.com.abg.customermanagementapp.application.adapter.rest.controller.customer.v1

import br.com.abg.customermanagementapp.Application
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.request.CreateCustomerRequest
import br.com.abg.customermanagementapp.domain.entity.customer.Address
import br.com.abg.customermanagementapp.domain.entity.customer.Customer
import br.com.abg.customermanagementapp.infrastructure.adapter.repository.customer.CustomerRepository
import br.com.abg.customermanagementapp.infrastructure.adapter.rest.zipcode.client.ZipCodeAddressClient
import br.com.abg.customermanagementapp.infrastructure.adapter.rest.zipcode.model.AddressByZipCodeResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.time.LocalDateTime

@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@TestPropertySource(locations = ["classpath:application.yml"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EmbeddedKafka(partitions = 1, brokerProperties = [ "listeners=PLAINTEXT://localhost:9092", "port=9092" ] )
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @MockBean
    private lateinit var zipCodeAddressClient: ZipCodeAddressClient

    private val customersEndpoint = "/v1/customers"
    private val document = "12345678900"
    private val mapper = ObjectMapper().registerModule(JavaTimeModule())

    @BeforeAll
    fun `initialize tests`() {
        customerRepository.save(mockCustomer(document))
    }
    @Test
    fun `should return http 404 for non existent customer`() {
        mockMvc.perform(MockMvcRequestBuilders.get(customersEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("document", "98765432100"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    fun `should return http 200 for existent customer`() {
        mockMvc.perform(MockMvcRequestBuilders.get(customersEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("document", document))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jhon"))
    }

    @Test
    fun `should create new customer`() {
        Mockito.`when`(zipCodeAddressClient.getAddressByZipCode("12345678"))
            .thenReturn(ResponseEntity.ok(mockZipAddressResponse()))

        val payload = mapper.writeValueAsString(mockCustomerRequest("12345678901"))
        mockMvc.perform(MockMvcRequestBuilders.post(customersEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @Test
    fun `should not create duplicated customer`() {
        Mockito.`when`(zipCodeAddressClient.getAddressByZipCode("12345678"))
            .thenReturn(ResponseEntity.ok(mockZipAddressResponse()))

        val payload = mapper.writeValueAsString(mockCustomerRequest(document))
        mockMvc.perform(MockMvcRequestBuilders.post(customersEndpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    private fun mockCustomerRequest(document: String) = CreateCustomerRequest(
        name = "Jhon",
        phone = "11-987654321",
        document = document,
        bornDate = LocalDate.now(),
        address = CreateCustomerRequest.Address(
            zipCode = "12345678",
            number = "5"
        )
    )
    private fun mockZipAddressResponse() = AddressByZipCodeResponse(
        cep = "12345678",
        localidade = "cidade",
        uf = "xp",
        ibge = "1234",
        siafi = "1234",
        ddd = "123",
        gia = "1234",
        logradouro = "1123",
        complemento = "1234",
        bairro = "1234"
    )
    private fun mockCustomer(document: String): Customer {
        val addr = Address()
        addr.zipCode = "1234"
        addr.number = "123"
        addr.street = "new street"
        addr.neighborhood = "asdbc"
        addr.city = "asdasd"
        addr.state = "uf"

        val customer = Customer()
        customer.name = "Jhon"
        customer.creationDate = LocalDateTime.now()
        customer.bornDate = LocalDate.now()
        customer.phone = "11-987654321"
        customer.document = document
        customer.address = addr

        return customer
    }
}