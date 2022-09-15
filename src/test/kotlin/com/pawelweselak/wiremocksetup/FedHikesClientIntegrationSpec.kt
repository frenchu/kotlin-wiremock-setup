package com.pawelweselak.wiremocksetup

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.wiremock.WireMockListener
import io.kotest.matchers.shouldBe
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigDecimal

/**
 * Integration test example with Kotest,
 * without setting up Spring context
 */
class FedHikesClientIntegrationSpec : ExpectSpec({

    val client = FedHikesClient(WebClient.create(serverBaseUrl))

    context("interests rates are not disastrously high") {
        val rates = 14.toBigDecimal()
        stubFedResponse(rates)
        expect("client gets the correct rates") {
            client.getRates() shouldBe FedRates(rates)
        }
    }

    listener(WireMockListener.perTest(server))
})

fun stubFedResponse(rates: BigDecimal): StubMapping =
    server.stubFor(
        get("/rates")
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ \"value\": \"$rates\" }")
            )
    )