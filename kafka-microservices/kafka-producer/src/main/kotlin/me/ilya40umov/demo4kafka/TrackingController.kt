package me.ilya40umov.demo4kafka

import me.ilya40umov.demo4kafka.models.TrackingEvent
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class TrackingController(
    private val kafkaTemplate: KafkaTemplate<String, TrackingEvent>
) {
    @PutMapping("/v1/events")
    fun recordTrackingEvents(@RequestBody events: List<TrackingEvent>): ResponseEntity<String> {
        val resultsFuture = CompletableFuture.allOf(
            *events.map { event ->
                kafkaTemplate.send("tracking-events-topics", event.id.toString(), event)
            }.toTypedArray()
        )
        resultsFuture.join()
        return ResponseEntity.ok("""{"status": "ok"}""")
    }
}