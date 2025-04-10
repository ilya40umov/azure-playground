provider "azurerm" {
  subscription_id = var.azure_subscription_id
  features {}
}

resource "azurerm_resource_group" "rg" {
  name     = "spring-boot-azure-blob-rg"
  location = var.azure_region
}

