resource "azurerm_storage_account" "storage" {
  name                     = "${replace(var.function_name, "/[^a-zA-Z0-9]+/", "")}storage"
  resource_group_name      = azurerm_resource_group.rg.name
  location                 = azurerm_resource_group.rg.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
}