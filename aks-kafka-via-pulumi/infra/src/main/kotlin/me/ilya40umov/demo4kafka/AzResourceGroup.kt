package me.ilya40umov.demo4kafka

import com.pulumi.azure.core.ResourceGroup
import com.pulumi.azure.core.ResourceGroupArgs
import com.pulumi.resources.ComponentResource
import com.pulumi.resources.CustomResourceOptions

class AzResourceGroup(
    name: String,
    location: String,
) : ComponentResource("demo4kafka:AzResourceGroup", name) {
    val rg = ResourceGroup(
        "$name-rg",
        ResourceGroupArgs.builder()
            .name("$name-rg")
            .location(location)
            .build(),
        CustomResourceOptions.builder()
            .parent(this)
            .build()
    )
}