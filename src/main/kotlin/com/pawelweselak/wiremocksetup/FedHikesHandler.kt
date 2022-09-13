package com.pawelweselak.wiremocksetup

import org.springframework.web.reactive.function.server.*

internal fun fedHikesHandler(client: FedHikesClient): suspend (ServerRequest) -> ServerResponse = {
    ServerResponse.ok().json().bodyValueAndAwait(
        client.getRates().toArmageddonStatus()
    )
}

private fun FedRates.toArmageddonStatus() = ArmageddonStatus(value > 15.toBigDecimal())

data class ArmageddonStatus(val areWeDoomedYet: Boolean)
