package my.ilya40umov.azblobstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootAzureBlobStorageApplication

fun main(args: Array<String>) {
	runApplication<SpringBootAzureBlobStorageApplication>(*args)
}
