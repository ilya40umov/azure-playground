package me.ilya40umov.demo4kafka

import com.pulumi.azure.containerservice.KubernetesCluster
import com.pulumi.azure.containerservice.KubernetesClusterArgs
import com.pulumi.azure.containerservice.inputs.KubernetesClusterDefaultNodePoolArgs
import com.pulumi.azure.containerservice.inputs.KubernetesClusterIdentityArgs
import com.pulumi.resources.ComponentResource

class AzAks(
    name: String,
    resourceGroup: AzResourceGroup,
    vnet: AzVnet,
) : ComponentResource("demo4kafka:AzAks", name) {
    val cluster = KubernetesCluster(
        "$name-aks",
        KubernetesClusterArgs.builder()
            .resourceGroupName(resourceGroup.rg.name())
            .location(resourceGroup.rg.location())
            .skuTier("Free")
            .roleBasedAccessControlEnabled(true)
            .identity(
                KubernetesClusterIdentityArgs.builder()
                    .type("SystemAssigned")
                    .build()
            )
            .dnsPrefix("$name-${name.hashCode()}")
            .defaultNodePool(
                KubernetesClusterDefaultNodePoolArgs.builder()
                    .name("pool0")
                    .nodeCount(1)
                    .vmSize("Standard_DS2_v2")
                    .vnetSubnetId(vnet.subnet.id())
                    .build()
            )
            .build()
    )
}