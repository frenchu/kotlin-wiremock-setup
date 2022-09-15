package com.pawelweselak.wiremocksetup

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(ApplicationConfiguration::class)
@ContextConfiguration(
    initializers = [CustomInitializer::class]
)
internal class ApplicationConfigurationIntegrationTest(
    @Autowired private val fedClient: FedHikesClient
) {
    @Test
    fun contextLoads() {
        fedClient shouldNotBe null
    }
}