package com.pawelweselak.wiremocksetup

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient

/**
 * End-to-end app test with JUnit5
 */
@ExtendWith(WireMockExtension::class)
internal class FedHikesClientIntegrationTest {

    private val client = FedHikesClient(WebClient.create(serverBaseUrl))

    @Test
    fun `should return the correct rates`() {
        // given
        val rates = 14
        stubFedResponse(rates)
        // when
        val response = runBlocking { client.getRates() }
        // then
        assert(response == FedRates(14.toBigDecimal()))
    }

    private fun stubFedResponse(rates: Int) {
        stubFor(
            get("/rates")
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"value\": \"$rates\" }")
                )
        )
    }
}

