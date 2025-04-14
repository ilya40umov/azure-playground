provider "azurerm" {
  subscription_id = var.azure_subscription_id
  features {}
}

resource "azurerm_resource_group" "rg" {
  name     = "azure-blob-storage-rg"
  location = var.azure_region
}

