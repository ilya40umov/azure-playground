package me.ilya40umov.demo4kafka

import com.pulumi.Pulumi
import com.pulumi.azure.core.ResourceGroup
import com.pulumi.azure.core.ResourceGroupArgs

fun main() {
    Pulumi.run {
        ResourceGroup(
            "demo4kafka-rg",
            ResourceGroupArgs.builder()
                .name("demo4kafka-rg")
                .location("westeurope")
                .build()
        )
    }
}