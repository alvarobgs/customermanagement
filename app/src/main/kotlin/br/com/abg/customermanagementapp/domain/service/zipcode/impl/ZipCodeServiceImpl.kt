package br.com.abg.customermanagementapp.domain.service.zipcode.impl

import br.com.abg.customermanagementapp.core.common.Messages
import br.com.abg.customermanagementapp.domain.adapter.output.rest.client.zipcodeaddress.ZipCodeAddressClient
import br.com.abg.customermanagementapp.domain.adapter.output.rest.client.zipcodeaddress.model.AddressByZipCodeResponse
import br.com.abg.customermanagementapp.domain.exceptions.InvalidArgumentException
import br.com.abg.customermanagementapp.domain.service.zipcode.ZipCodeService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ZipCodeServiceImpl(
 private val zipCodeAddressClient: ZipCodeAddressClient
) : ZipCodeService {

    private val logger = LoggerFactory.getLogger(ZipCodeServiceImpl::class.java)
    override fun fetchAddressByZipCode(zipCode: String): AddressByZipCodeResponse {
        logger.info("Fetching address for zipcode $zipCode")

        zipCode.takeIf { it.isNotBlank() } ?:
            throw InvalidArgumentException(Messages.findByKey("empty.zipcode.error", Messages.Language.PT))

        return runCatching {
            zipCodeAddressClient.getAddressByZipCode(zipCode).body!!
        }.onSuccess {
            logger.info("Successfully fetched zip code")
        }.onFailure {
            logger.error("Failed to fetch zip code", it)
        }.getOrThrow()
    }
}