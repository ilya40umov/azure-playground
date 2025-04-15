package me.ilya40umov.azmsg

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MessagingViaArmApplication

fun main(args: Array<String>) {
	runApplication<MessagingViaArmApplication>(*args)
}
