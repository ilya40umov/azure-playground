package me.ilya40umov.demo4kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaConsumerApplication

fun main(args: Array<String>) {
	runApplication<KafkaConsumerApplication>(*args)
}
