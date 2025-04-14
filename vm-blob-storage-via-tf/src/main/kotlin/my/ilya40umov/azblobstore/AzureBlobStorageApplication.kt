package my.ilya40umov.azblobstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AzureBlobStorageApplication

fun main(args: Array<String>) {
	runApplication<AzureBlobStorageApplication>(*args)
}
