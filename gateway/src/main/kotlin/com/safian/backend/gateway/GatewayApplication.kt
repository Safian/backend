package com.safian.backend.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.core.userdetails.User
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@EnableDiscoveryClient
@RestController
class GatewayApplication {
	@RequestMapping("/circuitbreakerfallback")
	fun circuitbreakerfallback(): String {
		return "This is a fallback"
	}

	@Bean
	fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator = builder.routes()
		.route { r: PredicateSpec ->
			r.path("/anything/**")
				.uri("lb://anything")
		}
		.build()


	@Bean
	@Throws(Exception::class)
	fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		return http.httpBasic().and()
			.csrf().disable()
			.authorizeExchange()
			.pathMatchers("/anything/**").authenticated()
			.anyExchange().permitAll()
			.and()
			.build()
	}

	@Bean
	fun reactiveUserDetailsService(): MapReactiveUserDetailsService {
		val user: UserDetails =
			User.withUsername("user").password("password").roles("USER").build()
		return MapReactiveUserDetailsService(user)
	}
}

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}
