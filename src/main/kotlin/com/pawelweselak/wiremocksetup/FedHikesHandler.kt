package com.pawelweselak.wiremocksetup

import org.springframework.web.reactive.function.server.*

internal fun mortgageHandler(client: FedHikesClient): suspend (ServerRequest) -> ServerResponse = {
    ServerResponse.ok().json().bodyValueAndAwait(
        client.getRates().toMortgageStatus()
    )
}

private fun FedRates.toMortgageStatus() = MortgageStatus(value > 15.toBigDecimal())

data class MortgageStatus(val areWeBankrupt: Boolean)
