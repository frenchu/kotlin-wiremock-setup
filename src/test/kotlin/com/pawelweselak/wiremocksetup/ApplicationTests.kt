package com.pawelweselak.wiremocksetup

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(WireMockExtension::class)
class ApplicationTests(@Autowired private val webClient: WebTestClient) : WiremockTestBase {

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
			.get().uri("/armageddon/status")
			.exchange()
			.expectStatus().isOk
			.expectBody<ArmageddonStatus>().isEqualTo(notDoomedYet)
	}

	@Test
	fun `should tell if armageddon is here`() {
		val rates = 16
		stubFor(get("/rates")
			.willReturn(aResponse()
				.withHeader("Content-Type", "application/json")
				.withBody("{ \"value\": $rates }")))
		webClient
			.get().uri("/armageddon/status")
			.exchange()
			.expectStatus().isOk
			.expectBody<ArmageddonStatus>().isEqualTo(alreadyDoomed)
	}
}

val alreadyDoomed = ArmageddonStatus(true)
val notDoomedYet = ArmageddonStatus(false)

