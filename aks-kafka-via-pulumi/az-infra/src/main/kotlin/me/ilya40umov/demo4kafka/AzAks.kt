package me.ilya40umov.demo4kafka

import com.pulumi.azure.authorization.Assignment
import com.pulumi.azure.authorization.AssignmentArgs
import com.pulumi.azure.containerservice.KubernetesCluster
import com.pulumi.azure.containerservice.KubernetesClusterArgs
import com.pulumi.azure.containerservice.inputs.KubernetesClusterDefaultNodePoolArgs
import com.pulumi.azure.containerservice.inputs.KubernetesClusterIdentityArgs
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzAks(
    name: String,
    resourceGroup: AzResourceGroup,
    vnet: AzVnet,
    acr: AzContainerRegistry,
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
            .oidcIssuerEnabled(true)
            .workloadIdentityEnabled(true)
            .build()/*,
        CustomResourceOptions.builder()
            .parent(this)
            .build()*/
    ).also { cluster ->
        Assignment(
            "$name-aks-acr-pull-access",
            AssignmentArgs
                .builder()
                .principalId(cluster.kubeletIdentity().applyValue { it.objectId().get() })
                .roleDefinitionName("AcrPull")
                .scope(acr.acr.id())
                .build(),
            CustomResourceOptions.builder()
                .parent(this)
                .build()
        )
    }
}