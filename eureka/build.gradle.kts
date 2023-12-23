plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
}

version = "0.0.1-SNAPSHOT"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:4.0.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}

