package ch.brontofundus.demos.quartz

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest
@Testcontainers
internal class QuartzDemoApplicationTests {

	@Autowired
	lateinit var flyway: Flyway

	@Test
	fun `context loads and Flyway Migrations have been run`() {
		println("testing if context loads...")

		Assertions.assertEquals("1.0.2", flyway.info().current().version.version)
	}

	companion object {

		@Container
		var postgres = PostgreSQLContainer(DockerImageName.parse("postgres:15.3-alpine"))

		@JvmStatic
		@DynamicPropertySource
		fun properties(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url") { postgres.jdbcUrl }
			registry.add("spring.datasource.username") { postgres.username }
			registry.add("spring.datasource.password") { postgres.password }
		}
	}

}
