package me.ilya40umov.demo4kafka

import com.pulumi.azure.authorization.Assignment
import com.pulumi.azure.authorization.AssignmentArgs
import com.pulumi.azure.core.outputs.GetClientConfigResult
import com.pulumi.azure.keyvault.KeyVault
import com.pulumi.azure.keyvault.KeyVaultArgs
import com.pulumi.core.Output
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzKeyVault(
    name: String,
    resourceGroup: AzResourceGroup,
    clientConfig: Output<GetClientConfigResult>
) : ComponentResource("demo4kafka:AzKeyVault", name) {
    private val vaultUniqueName = ("${name}${name.hashCode()}kv").replace("[^a-z0-9]".toRegex(), "")
    val vault = KeyVault(
        "$name-kv",
        KeyVaultArgs.builder()
            .name(vaultUniqueName)
            .resourceGroupName(resourceGroup.rg.name())
            .location(resourceGroup.rg.location())
            .skuName("standard")
            .tenantId(clientConfig.applyValue { it.tenantId() })
            .publicNetworkAccessEnabled(true)
            .softDeleteRetentionDays(7)
            .purgeProtectionEnabled(false)
            .enableRbacAuthorization(true)
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )

    val adminAssignment = Assignment(
        "$name-kv-admin-assignment",
        AssignmentArgs.Builder()
            .scope(vault.id())
            .roleDefinitionName("Key Vault Administrator")
            .principalId(clientConfig.applyValue { it.objectId() })
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
}