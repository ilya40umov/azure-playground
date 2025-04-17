package me.ilya40umov.demo4kafka

import kotlinx.coroutines.future.await
import me.ilya40umov.demo4kafka.config.TopicsConfig
import me.ilya40umov.demo4kafka.models.TrackingEvent
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TrackingEventsController(
    private val kafkaTemplate: KafkaTemplate<String, TrackingEvent>,
    private val topicProps: TopicsConfig.TrackingEventsTopicProps
) {
    private val logger = LoggerFactory.getLogger(TrackingEventsController::class.java)

    @PutMapping("/v1/events")
    suspend fun recordTrackingEvents(@RequestBody events: List<TrackingEvent>): ResponseEntity<String> {
        logger.info("Recording ${events.size} events...")
        val results = events.map { event ->
            kafkaTemplate.send(topicProps.name, event.userId.toString(), event).await()
        }
        results.forEach { result ->
            logger.info(
                "Recorded event under partition {}, offset {}",
                result.recordMetadata.partition(),
                result.recordMetadata.offset()
            )
        }
        return ResponseEntity.ok("""{"status": "ok"}""")
    }
}