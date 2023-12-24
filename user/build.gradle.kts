plugins {
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
}

version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_19
}

dependencies {
	implementation( project(":library"))
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.0")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	runtimeOnly("org.mariadb:r2dbc-mariadb:1.1.3")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}