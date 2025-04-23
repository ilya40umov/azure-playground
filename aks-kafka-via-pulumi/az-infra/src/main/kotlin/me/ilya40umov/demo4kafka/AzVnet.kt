package me.ilya40umov.demo4kafka

import com.pulumi.azure.network.*
import com.pulumi.azure.network.inputs.NetworkSecurityGroupSecurityRuleArgs
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzVnet(
    name: String,
    resourceGroup: AzResourceGroup,
    allowedInboundPorts: List<Int>
) : ComponentResource("demo4kafka:AzVnet", name) {
    val vnet = VirtualNetwork(
        "$name-vnet",
        VirtualNetworkArgs.builder()
            .addressSpaces(listOf("10.1.0.0/16"))
            .location(resourceGroup.rg.location())
            .resourceGroupName(resourceGroup.rg.name())
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )

    val subnet = Subnet(
        "$name-subnet",
        SubnetArgs.builder()
            .resourceGroupName(resourceGroup.rg.name())
            .virtualNetworkName(vnet.name())
            .addressPrefixes("10.1.0.0/20")
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )

    val nsg = NetworkSecurityGroup(
        "$name-nsg",
        NetworkSecurityGroupArgs.builder()
            .resourceGroupName(resourceGroup.rg.name())
            .location(resourceGroup.rg.location())
            .securityRules(
                allowedInboundPorts.mapIndexed { idx, port ->
                    NetworkSecurityGroupSecurityRuleArgs.Builder()
                        .name("AllowInternet${port}Inbound")
                        .access("Allow")
                        .direction("Inbound")
                        .priority(100 + idx)
                        .protocol("Tcp")
                        .sourcePortRange("*")
                        .destinationPortRange("$port")
                        .sourceAddressPrefix("Internet")
                        .destinationAddressPrefix("*")
                        .build()
                }
            )
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    ).also { nsg ->
        SubnetNetworkSecurityGroupAssociation(
            "$name-nsg-association",
            SubnetNetworkSecurityGroupAssociationArgs.builder()
                .subnetId(subnet.id())
                .networkSecurityGroupId(nsg.id())
                .build(),
            CustomResourceOptions.builder()
                .parent(this)
                .build()
        )
    }
}