plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
}

version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_19
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}
