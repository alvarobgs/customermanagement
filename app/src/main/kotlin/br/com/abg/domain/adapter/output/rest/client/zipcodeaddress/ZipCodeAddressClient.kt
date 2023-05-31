package br.com.abg.domain.adapter.output.rest.client.zipcodeaddress

import br.com.abg.domain.adapter.output.rest.client.zipcodeaddress.model.AddressByZipCodeResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "zipcode-feign-client",
    url = "\${endpoint.zipcode-ws}"
)
interface ZipCodeAddressClient {

    @GetMapping("{zip}/json/")
    fun getAddressByZipCode(
        @PathVariable(name = "zip") zipCode: String
    ): ResponseEntity<AddressByZipCodeResponse>
}