package me.ilya40umov.demo4kafka.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class TopicsConfig {
    @Bean
    fun trackingEventsTopic() =
        TopicBuilder.name("tracking-events-topics")
            .partitions(3)
            .replicas(1)
            .build()
}