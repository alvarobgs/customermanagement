package br.com.abg.domain.adapter.input.rest.controller.customer

import br.com.abg.domain.adapter.input.rest.controller.customer.model.request.CreateCustomerRequest
import br.com.abg.domain.adapter.input.rest.controller.customer.model.response.CreateCustomerResponse
import br.com.abg.domain.adapter.input.rest.controller.customer.model.response.FindCustomerByDocumentResponse
import br.com.abg.domain.adapter.input.rest.mapper.customer.mapToCreateCustomerResponse
import br.com.abg.domain.adapter.input.rest.mapper.customer.mapToFindCustomerByDocumentResponse
import br.com.abg.domain.tools.AvroGen
import br.com.abg.domain.usecase.customer.CreateCustomerUseCase
import br.com.abg.domain.usecase.customer.FindCustomerUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Validated
@RestController
@RequestMapping("/customer")
class CustomerController(
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val findCustomerUseCase: FindCustomerUseCase
) {

    @GetMapping
    fun findCustomerByDocument(
        @RequestParam(name = "document") document: String
    ): ResponseEntity<FindCustomerByDocumentResponse> {

        return findCustomerUseCase.execute(document)?.let {
            ResponseEntity.ok(it.mapToFindCustomerByDocumentResponse())
        } ?: ResponseEntity.notFound().build()
    }

    @PostMapping(name = "/create")
    fun createCustomer(
        @RequestBody body: CreateCustomerRequest
    ): ResponseEntity<CreateCustomerResponse> {

        val customer = createCustomerUseCase.execute(body)

        return ResponseEntity(customer.mapToCreateCustomerResponse(), HttpStatus.CREATED)
    }
}