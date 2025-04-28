resource "azurerm_log_analytics_workspace" "workspace" {
  name                = "azure-func-logs"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  sku                 = "PerGB2018"
  retention_in_days   = 30
}

resource "azurerm_application_insights" "insights" {
  name                = "azure-func-insights"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  application_type    = "web"
  workspace_id        = azurerm_log_analytics_workspace.workspace.id
}

resource "azurerm_monitor_diagnostic_setting" "func-app-logs" {
  name                       = "azure-func-logs"
  target_resource_id         = azurerm_linux_function_app.function.id
  log_analytics_workspace_id = azurerm_log_analytics_workspace.workspace.id

  enabled_log  {
    category = "FunctionAppLogs"
  }
}