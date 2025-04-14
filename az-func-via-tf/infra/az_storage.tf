resource "azurerm_storage_account" "storage" {
  name                     = "funcstora0c87e61"
  resource_group_name      = azurerm_resource_group.rg.name
  location                 = azurerm_resource_group.rg.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
}

resource "azurerm_storage_container" "code" {
  name                  = "azure-func-code"
  storage_account_id  = azurerm_storage_account.storage.id
  container_access_type = "blob"
}

resource "azurerm_storage_blob" "zip" {
  name                   = "function${filemd5("${path.module}/../function.zip")}.zip"
  content_md5            = filemd5("${path.module}/../function.zip")
  storage_account_name   = azurerm_storage_account.storage.name
  storage_container_name = azurerm_storage_container.code.name
  type                   = "Block"
  source                 = "${path.module}/../function.zip"
}