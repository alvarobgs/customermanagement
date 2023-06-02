package br.com.abg.customermanagementapp.application.adapter.rest.controller.customer.v1

import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.request.CreateCustomerRequest
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response.CreateCustomerResponse
import br.com.abg.customermanagementapp.application.adapter.rest.model.customer.v1.response.FindCustomerByDocumentResponse
import br.com.abg.customermanagementapp.domain.service.customer.CustomerService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/v1/customers")
class CustomerController(
    private val service: CustomerService
) {

    @GetMapping
    fun findCustomerByDocument(
        @RequestParam(name = "document") @NotBlank @Pattern(regexp = "[0-9]{11}") document: String
    ): ResponseEntity<FindCustomerByDocumentResponse> {

        return service.findByDocument(document)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping(name = "/create")
    fun createCustomer(
        @RequestBody @Valid body: CreateCustomerRequest
    ): ResponseEntity<CreateCustomerResponse> {
        return ResponseEntity(service.createNewCustomer(body), HttpStatus.CREATED)
    }
}