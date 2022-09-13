package com.pawelweselak.wiremocksetup

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.coRouter

@Configuration
@EnableConfigurationProperties(FedHikesClientProperties::class)
class ApplicationConfiguration {

    @Bean
    internal fun webClient(properties: FedHikesClientProperties): WebClient = WebClient.create(properties.baseUrl)

    @Bean
    internal fun fedHikesClient(client: WebClient): FedHikesClient = FedHikesClient(client)

    @Bean
    internal fun home(fedHikesClient: FedHikesClient) = coRouter {
        GET("/armageddon/status", fedHikesHandler(fedHikesClient))
    }
}

@ConfigurationProperties(prefix = "fed-hikes-client")
@ConstructorBinding
internal data class FedHikesClientProperties(val baseUrl: String)
