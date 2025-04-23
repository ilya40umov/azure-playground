package me.ilya40umov.demo4kafka

import com.pulumi.azure.armmsi.FederatedIdentityCredential
import com.pulumi.azure.armmsi.FederatedIdentityCredentialArgs
import com.pulumi.azure.authorization.Assignment
import com.pulumi.azure.authorization.AssignmentArgs
import com.pulumi.azure.authorization.UserAssignedIdentity
import com.pulumi.azure.authorization.UserAssignedIdentityArgs
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzWorkloadIdentity(
    name: String,
    resourceGroup: AzResourceGroup,
    aks: AzAks,
    keyVault: AzKeyVault,
    namespace: String,
    serviceAccount: String,
) : ComponentResource("demo4kafka:AzWorkloadIdentity", name) {
    val identity = UserAssignedIdentity(
        "$name-identity",
        UserAssignedIdentityArgs.builder()
            .name("$name-workload-identity")
            .resourceGroupName(resourceGroup.rg.name())
            .location(resourceGroup.rg.location())
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
    val identityCredential = FederatedIdentityCredential(
        "$name-identity-credential",
        FederatedIdentityCredentialArgs.builder()
            .name("$name-identity-credential")
            .resourceGroupName(resourceGroup.rg.name())
            .audience("api://AzureADTokenExchange")
            .issuer(aks.cluster.oidcIssuerUrl())
            .parentId(identity.id())
            .subject("system:serviceaccount:$namespace:$serviceAccount") // namespace + service account name
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
    val keyVaultAssignment = Assignment(
        "$name-workload-kv-access",
        AssignmentArgs.Builder()
            .scope(keyVault.vault.id())
            .roleDefinitionName("Key Vault Secrets User")
            .principalId(identity.principalId())
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
}