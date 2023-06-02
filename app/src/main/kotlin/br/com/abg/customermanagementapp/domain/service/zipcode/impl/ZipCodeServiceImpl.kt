package br.com.abg.customermanagementapp.domain.service.zipcode.impl

import br.com.abg.customermanagementapp.domain.exceptions.InvalidArgumentException
import br.com.abg.customermanagementapp.domain.service.zipcode.ZipCodeService
import br.com.abg.customermanagementapp.infrastructure.adapter.rest.zipcode.client.ZipCodeAddressClient
import br.com.abg.customermanagementapp.infrastructure.adapter.rest.zipcode.model.AddressByZipCodeResponse
import br.com.abg.customermanagementapp.infrastructure.common.Messages
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
            throw InvalidArgumentException(Messages.findByKey("empty.zipcode.error"))

        return runCatching {
            zipCodeAddressClient.getAddressByZipCode(zipCode).body!!
        }.onSuccess {
            logger.info("Successfully fetched zip code")
        }.onFailure {
            logger.error("Failed to fetch zip code", it)
        }.getOrThrow()
    }
}