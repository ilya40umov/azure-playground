package me.ilya40umov.demo4kafka

import com.pulumi.Pulumi
import com.pulumi.azure.core.CoreFunctions.getClientConfig

fun main() {
    Pulumi.run {
        val clientConfig = getClientConfig()
        val resourceGroup = AzResourceGroup(
            name = "demo4kafka",
            location = "westeurope",
        )
        val keyVault = AzKeyVault(
            name = "demo4kafka",
            resourceGroup = resourceGroup,
            clientConfig = clientConfig
        )
        val eventHubNs = AzEventHubNamespace(
            name = "demo4kafka",
            resourceGroup = resourceGroup,
            keyVault = keyVault
        )
        val vnet = AzVnet(
            name = "demo4kafka",
            resourceGroup = resourceGroup
        )
        val acr = AzContainerRegistry(
            name = "demo4kafka",
            resourceGroup = resourceGroup
        )
        val aks = AzAks(
            name = "demo4kafka",
            resourceGroup = resourceGroup,
            vnet = vnet
        )
    }
}