package com.pawelweselak.wiremocksetup

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.math.BigDecimal


internal class FedHikesClient(private val webClient: WebClient) {
    suspend fun getRates(): FedRates = webClient
        .get()
        .uri("/rates")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .awaitBody()
}

internal data class FedRates(val value: BigDecimal)
