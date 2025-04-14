provider "azurerm" {
  subscription_id = var.azure_subscription_id
  features {}
}

resource "azurerm_resource_group" "rg" {
  name     = "app-service-tf-rg"
  location = var.azure_region
}