package br.com.abg.customermanagementapp.domain.service.zipcode

import br.com.abg.customermanagementapp.domain.adapter.output.rest.client.zipcodeaddress.model.AddressByZipCodeResponse

interface ZipCodeService {

    fun fetchAddressByZipCode(zipCode: String): AddressByZipCodeResponse
}