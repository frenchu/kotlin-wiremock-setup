package com.pawelweselak.wiremocksetup

import com.github.tomakehurst.wiremock.client.WireMock
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.wiremock.WireMockListener
import io.kotest.matchers.shouldBe
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration

@Import(ApplicationConfiguration::class)
@ContextConfiguration(
    initializers = [CustomInitializer::class]
)
internal class ApplicationConfigurationIntegrationSpec(
    private val fedClient: FedHikesClient
) : ExpectSpec({

    context("loads") {
        server.stubFor(
            WireMock.get("/rates")
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"value\": \"12\" }")
                )
        )
        expect("fed hikes") {
            fedClient.getRates() shouldBe FedRates(12.toBigDecimal())
        }
    }

    listener(WireMockListener.perTest(server))

    extension(SpringExtension)
})
