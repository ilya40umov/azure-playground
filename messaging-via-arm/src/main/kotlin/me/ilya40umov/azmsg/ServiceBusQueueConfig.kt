package me.ilya40umov.azmsg

import com.azure.messaging.servicebus.ServiceBusErrorContext
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusErrorHandler
import com.azure.spring.cloud.service.servicebus.consumer.ServiceBusRecordMessageListener
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration(proxyBeanMethods = false)
class ServiceBusQueueConfig {

    private val logger = LoggerFactory.getLogger(ServiceBusQueueConfig::class.java)

    @Bean
    fun messageListener(): ServiceBusRecordMessageListener {
        return ServiceBusRecordMessageListener { context: ServiceBusReceivedMessageContext ->
            val message = context.message
            logger.info("Received from Service Bus queue: ${message.body}")
        }
    }

    @Bean
    fun processError(): ServiceBusErrorHandler {
        return ServiceBusErrorHandler { context: ServiceBusErrorContext ->
            val message = context
            logger.warn("Failed to receive message: ${context.entityPath}", context.exception)
        }
    }

}