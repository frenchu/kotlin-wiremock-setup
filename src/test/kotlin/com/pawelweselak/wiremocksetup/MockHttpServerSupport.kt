package com.pawelweselak.wiremocksetup

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import com.github.tomakehurst.wiremock.junit5.WireMockExtension as TomakehurstWireMockExtension
import java.net.ServerSocket

interface WiremockTestBase {

    companion object {

        @DynamicPropertySource
        @JvmStatic
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("fed-hikes-client.baseUrl") { serverBaseUrl }
        }
    }
}

object WireMockExtension : TomakehurstWireMockExtension(
    extensionOptions()
        .options(serverOptions)
        .configureStaticDsl(true)
)

const val RANDOM_PORT = 0

val serverPort = ServerSocket(RANDOM_PORT).apply { close() }.localPort
val serverBaseUrl = "http://localhost:$serverPort"
val serverOptions: WireMockConfiguration = wireMockConfig().port(serverPort)
val server = WireMockServer(serverOptions)




