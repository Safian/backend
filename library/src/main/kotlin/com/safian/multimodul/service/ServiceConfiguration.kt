package com.safian.multimodul.service

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(ServiceProperties::class)
class ServiceConfiguration {
    private var serviceProperties: ServiceProperties? = null

    fun serviceConfiguration(serviceProperties: ServiceProperties?) {
        this.serviceProperties = serviceProperties
    }

    fun message(): String {
        return serviceProperties?.message ?: ""
    }
}