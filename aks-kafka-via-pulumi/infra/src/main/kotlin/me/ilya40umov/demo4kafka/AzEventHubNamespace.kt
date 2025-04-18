package me.ilya40umov.demo4kafka

import com.pulumi.azure.eventhub.EventHub
import com.pulumi.azure.eventhub.EventHubArgs
import com.pulumi.azure.eventhub.EventHubNamespace
import com.pulumi.azure.eventhub.EventHubNamespaceArgs
import com.pulumi.azure.keyvault.Secret
import com.pulumi.azure.keyvault.SecretArgs
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzEventHubNamespace(
    name: String,
    resourceGroup: AzResourceGroup,
    keyVault: AzKeyVault
) : ComponentResource("demo4kafka:AzEventHubNamespace", name) {
    val ns = EventHubNamespace(
        "$name-ns",
        EventHubNamespaceArgs.builder()
            .resourceGroupName(resourceGroup.rg.name())
            .location(resourceGroup.rg.location())
            .sku("Standard")
            .capacity(1)
            .publicNetworkAccessEnabled(true)
            .localAuthenticationEnabled(true)
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    ).also { ns ->
        Secret(
            "$name-ns-bootstrap-servers-secret",
            SecretArgs.builder()
                .name("event-hub-ns-bootstrap-servers")
                .keyVaultId(keyVault.vault.id())
                .value(ns.name().applyValue { "$it.servicebus.windows.net:9093" })
                .build(),
            CustomResourceOptions.builder()
                .parent(this)
                .dependsOn(keyVault.adminAssignment)
                .build()
        )
        Secret(
            "$name-ns-conn-secret",
            SecretArgs.builder()
                .name("event-hub-ns-connection")
                .keyVaultId(keyVault.vault.id())
                .value(ns.defaultPrimaryConnectionString())
                .build(),
            CustomResourceOptions.builder()
                .parent(this)
                .dependsOn(keyVault.adminAssignment)
                .build()
        )
    }

    val trackingEvents = EventHub(
        "tracking-events-hub",
        EventHubArgs.builder()
            .name("tracking-events")
            .namespaceId(ns.id())
            .partitionCount(3)
            .messageRetention(7)
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )

    val trackingEventsDlt = EventHub(
        "tracking-events-dlt-hub",
        EventHubArgs.builder()
            .name("tracking-events-dlt")
            .namespaceId(ns.id())
            .partitionCount(1)
            .messageRetention(7)
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
}