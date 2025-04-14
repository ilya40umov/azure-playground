package me.ilya40umov.demo4kafka

import me.ilya40umov.demo4kafka.models.TrackingEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TrackingConsumer {

    @KafkaListener(topics = ["tracking-events-topics"])
    fun processTrackingEvent(event: TrackingEvent) {
        println(event)
    }

}