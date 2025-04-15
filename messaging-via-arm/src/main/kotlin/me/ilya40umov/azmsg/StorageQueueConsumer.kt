package me.ilya40umov.azmsg

import com.azure.core.util.Base64Util
import com.azure.storage.queue.QueueClient
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class StorageQueueConsumer(
    private val queueClient: QueueClient
) {
    private val logger = LoggerFactory.getLogger(StorageQueueConsumer::class.java)

    @Scheduled(fixedRate = 100L)
    fun consumeMessages() {
        queueClient.receiveMessages(10).forEach { message ->
            logger.info("Received from Storage Queue: ${Base64Util.decode(message.body.toBytes()).toString(Charsets.UTF_8)}")
            queueClient.deleteMessage(message.messageId, message.popReceipt)
        }
    }
}