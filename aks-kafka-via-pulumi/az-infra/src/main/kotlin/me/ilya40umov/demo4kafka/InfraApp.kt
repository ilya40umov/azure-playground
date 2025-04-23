package me.ilya40umov.demo4kafka

import com.pulumi.Pulumi
import com.pulumi.azure.core.CoreFunctions.getClientConfig

fun main() {
    Pulumi.run { ctx ->
        val baseName = "demo4kafka"
        val location = "westeurope"

        val clientConfig = getClientConfig()

        val resourceGroup = AzResourceGroup(
            name = baseName,
            location = location,
        )
        val keyVault = AzKeyVault(
            name = baseName,
            resourceGroup = resourceGroup,
            clientConfig = clientConfig
        )
        val eventHubNs = AzEventHubNamespace(
            name = baseName,
            resourceGroup = resourceGroup,
            keyVault = keyVault
        )
        val vnet = AzVnet(
            name = baseName,
            resourceGroup = resourceGroup,
            allowedInboundPorts = listOf(8080)
        )
        val acr = AzContainerRegistry(
            name = baseName,
            resourceGroup = resourceGroup
        )
        val aks = AzAks(
            name = baseName,
            resourceGroup = resourceGroup,
            vnet = vnet,
            acr = acr
        )
        val workloadIdentity = AzWorkloadIdentity(
            name = baseName,
            resourceGroup = resourceGroup,
            aks = aks,
            keyVault = keyVault,
            namespace = "default",
            serviceAccount = baseName,
        )

        ctx.export("ACR_LOGIN_SERVER", acr.acr.loginServer())
        ctx.export("WORKLOAD_CLIENT_ID", workloadIdentity.identity.clientId())
    }
}