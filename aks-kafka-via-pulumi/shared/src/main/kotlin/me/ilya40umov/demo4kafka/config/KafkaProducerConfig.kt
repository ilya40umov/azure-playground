package me.ilya40umov.demo4kafka.config

import me.ilya40umov.demo4kafka.models.TrackingEvent
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig {

    @Bean
    fun tracingEventsProducerFactory(
        kafkaProperties: KafkaProperties,
        connectionDetails: KafkaConnectionDetails,
        sslBundles: ObjectProvider<SslBundles>
    ): ProducerFactory<String, TrackingEvent> {
        val producerProperties = kafkaProperties.buildProducerProperties(sslBundles.getIfAvailable()).apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connectionDetails.producerBootstrapServers)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
            put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true)
        }
        return DefaultKafkaProducerFactory<String, TrackingEvent>(producerProperties).apply {
            val transactionIdPrefix: String? = kafkaProperties.producer.transactionIdPrefix
            if (transactionIdPrefix != null) {
                setTransactionIdPrefix(transactionIdPrefix)
            }
        }
    }

    @Bean
    fun trackingEventTemplate(
        tracingEventsProducerFactory: ProducerFactory<String, TrackingEvent>
    ): KafkaTemplate<String, TrackingEvent> {
        return KafkaTemplate(tracingEventsProducerFactory)
    }

}