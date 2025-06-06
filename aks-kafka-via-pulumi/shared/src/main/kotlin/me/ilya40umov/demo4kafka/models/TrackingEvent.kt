package me.ilya40umov.demo4kafka.models

import java.util.UUID

data class TrackingEvent(
    val eventId: UUID,
    val userId: Long,
    val eventType: String,
    val dimensions: Map<String, Any> = emptyMap(),
    val payload: Map<String, Any> = emptyMap()
)