package me.ilya40umov.demo4kafka.config

import me.ilya40umov.demo4kafka.models.TrackingEvent
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun trackingEventsConsumerFactory(
        kafkaProperties: KafkaProperties,
        connectionDetails: KafkaConnectionDetails,
        sslBundles: ObjectProvider<SslBundles?>
    ): DefaultKafkaConsumerFactory<*, *> {
        val consumerProperties = kafkaProperties.buildConsumerProperties(sslBundles.getIfAvailable()).apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, connectionDetails.consumerBootstrapServers)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer::class.java)
            put(JsonDeserializer.VALUE_DEFAULT_TYPE, TrackingEvent::class.java)
            // "me.ilya40umov.demo4kafka.models"
            put(JsonDeserializer.TRUSTED_PACKAGES, TrackingEvent::class.java.packageName)
        }
        return DefaultKafkaConsumerFactory<String, TrackingEvent>(consumerProperties)
    }

}