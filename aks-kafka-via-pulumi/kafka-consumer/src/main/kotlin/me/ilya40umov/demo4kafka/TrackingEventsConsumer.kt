package me.ilya40umov.demo4kafka

import me.ilya40umov.demo4kafka.models.TrackingEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class TrackingEventsConsumer {

    private val logger = LoggerFactory.getLogger(TrackingEventsConsumer::class.java)

    @RetryableTopic(attempts = "1", dltStrategy = DltStrategy.FAIL_ON_ERROR)
    @KafkaListener(topics = ["\${app.tracking-events-topic.name}"], concurrency = "3")
    fun processTrackingEvent(event: TrackingEvent, @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int) {
        Thread.sleep(50L)
        logger.info("(partition: $partition) Processed '${event.eventType}' for user '${event.userId}'")
    }

    @DltHandler
    @Suppress("unused")
    fun processDltEvent(event: TrackingEvent) {
        logger.warn("Failed to process '${event.eventType}' for user '${event.userId}'")
    }

}