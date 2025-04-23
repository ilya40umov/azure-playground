package me.ilya40umov.demo4kafka

import com.pulumi.azure.containerservice.Registry
import com.pulumi.azure.containerservice.RegistryArgs
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzContainerRegistry(
    name: String,
    resourceGroup: AzResourceGroup,
) : ComponentResource("demo4kafka:AzContainerRegistry", name) {
    private val acrUniqueName = ("${name}${name.hashCode()}acr").replace("[^a-z0-9]".toRegex(), "")
    val acr = Registry(
        "${name}-acr",
        RegistryArgs.builder()
            .name(acrUniqueName)
            .resourceGroupName(resourceGroup.rg.name())
            .location(resourceGroup.rg.location())
            .sku("Standard")
            .adminEnabled(true)
            .publicNetworkAccessEnabled(true)
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
}