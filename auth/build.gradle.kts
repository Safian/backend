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
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer:4.0.1")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.0.0")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")

	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")

	implementation("org.mariadb.jdbc:mariadb-java-client:3.1.2")

	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	compileOnly("org.projectlombok:lombok:1.18.26")
	//developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	testImplementation("org.springframework.security:spring-security-test:6.0.2")
}
