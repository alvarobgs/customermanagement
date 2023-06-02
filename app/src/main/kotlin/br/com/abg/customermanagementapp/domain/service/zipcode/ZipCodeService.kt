package br.com.abg.customermanagementapp.domain.service.zipcode

import br.com.abg.customermanagementapp.infrastructure.adapter.rest.zipcode.model.AddressByZipCodeResponse

interface ZipCodeService {

    fun fetchAddressByZipCode(zipCode: String): AddressByZipCodeResponse
}