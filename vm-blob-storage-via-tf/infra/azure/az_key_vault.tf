resource "azurerm_key_vault" "vault" {
  name                        = var.key_valut_name
  location                    = azurerm_resource_group.rg.location
  resource_group_name         = azurerm_resource_group.rg.name
  tenant_id                   = data.azurerm_client_config.current.tenant_id
  sku_name                    = "standard"
  purge_protection_enabled    = false
  soft_delete_retention_days  = 7
  enable_rbac_authorization   = true
}

resource "azurerm_role_assignment" "owner_access" {
  principal_id   = data.azurerm_client_config.current.object_id
  role_definition_name = "Key Vault Administrator"
  scope           = azurerm_key_vault.vault.id
}

resource "azurerm_key_vault_secret" "storage_key" {
  name         = "storage-access-key"
  key_vault_id = azurerm_key_vault.vault.id
  value        = azurerm_storage_account.storage.primary_access_key
  depends_on = [azurerm_role_assignment.owner_access]
}
