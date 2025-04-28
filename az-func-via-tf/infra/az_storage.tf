resource "azurerm_storage_account" "storage" {
  name                     = "funcstora0c87e61"
  resource_group_name      = azurerm_resource_group.rg.name
  location                 = azurerm_resource_group.rg.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
}