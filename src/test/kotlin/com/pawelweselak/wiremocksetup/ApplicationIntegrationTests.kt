package com.pawelweselak.wiremocksetup

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

/**
 * App end-to-end test with JUnit5
 */
@SpringBootTest
@ContextConfiguration(
	initializers = [CustomInitializer::class]
)
@AutoConfigureWebTestClient
@ExtendWith(WireMockExtension::class)
class ApplicationIntegrationTests(@Autowired private val webClient: WebTestClient) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `should tell if armageddon is yet come`() {
		val rates = 14
		stubFor(get("/rates")
			.willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"value\": $rates }")))
		webClient
			.get().uri("/mortgage/status")
			.exchange()
			.expectStatus().isOk
			.expectBody<MortgageStatus>().isEqualTo(stillSafe)
	}

	@Test
	fun `should tell if armageddon is here`() {
		val rates = 16
		stubFor(get("/rates")
			.willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"value\": $rates }")))
		webClient
			.get().uri("/mortgage/status")
			.exchange()
			.expectStatus().isOk
			.expectBody<MortgageStatus>().isEqualTo(bankrupt)
	}
}

val bankrupt = MortgageStatus(true)
val stillSafe = MortgageStatus(false)

