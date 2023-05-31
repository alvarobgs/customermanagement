package br.com.abg.domain.service.zipcode

import br.com.abg.domain.adapter.output.rest.client.zipcodeaddress.model.AddressByZipCodeResponse

interface ZipCodeService {

    fun fetchAddressByZipCode(zipCode: String): AddressByZipCodeResponse
}