package br.com.abg.customermanagementapp.domain.adapter.input.rest.controller.customer

import br.com.abg.customermanagementapp.Application
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [Application::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


}