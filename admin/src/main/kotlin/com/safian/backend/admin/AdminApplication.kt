package com.safian.backend.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["com.safian.multimodul"])
@EntityScan(basePackages = ["com.safian.multimodul"])
@EnableJpaRepositories(basePackages = ["com.safian.multimodul"])
class AdminApplication

fun main(args: Array<String>) {
	runApplication<AdminApplication>(*args)
}
