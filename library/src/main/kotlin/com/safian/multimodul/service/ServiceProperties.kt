package com.safian.multimodul.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("service")
class ServiceProperties {
    lateinit var message: String
}