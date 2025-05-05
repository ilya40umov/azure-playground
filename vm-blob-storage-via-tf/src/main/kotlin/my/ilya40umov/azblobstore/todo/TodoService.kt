package my.ilya40umov.azblobstore.todo

import com.azure.core.util.BinaryData
import com.azure.storage.blob.BlobServiceClient
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val blobServiceClient: BlobServiceClient
) {

    private val containerName: String = "todos"

    fun saveTodo(name: String, text: String) {
        val containerClient = blobServiceClient.getBlobContainerClient(containerName)
        if (!containerClient.exists()) {
            containerClient.create()
        }

        val blobClient = containerClient.getBlobClient(name)
        try {
            blobClient.upload(BinaryData.fromString(text), true)
        } catch (e: Exception) {
            throw RuntimeException("Failed to upload data to blob", e)
        }
    }

    fun getTodo(name: String): String? {
        val containerClient = blobServiceClient.getBlobContainerClient(containerName)
        val blobClient = containerClient.getBlobClient(name)

        if (!blobClient.exists()) {
            return null
        }

        return blobClient.downloadContent().toString()
    }

}