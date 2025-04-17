package me.ilya40umov.demo4kafka.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
@EnableConfigurationProperties(TopicsConfig.TrackingEventsTopicProps::class)
class TopicsConfig {

    @ConfigurationProperties("app.tracking-events-topic")
    data class TrackingEventsTopicProps(
        val name: String,
        val partitions: Int,
        val replicas: Int
    )

    @Bean
    fun trackingEventsTopic(topicProps: TrackingEventsTopicProps) =
        TopicBuilder.name(topicProps.name)
            .partitions(topicProps.partitions)
            .replicas(topicProps.replicas)
            .build()
}