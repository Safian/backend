plugins {
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
}

java {
	sourceCompatibility = JavaVersion.VERSION_19
}

version = "0.0.1-SNAPSHOT"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-webflux:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.0.3")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:3.0.0")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.0")
	implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	testImplementation("io.projectreactor:reactor-test:3.5.4")
}
