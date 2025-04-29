resource "azurerm_log_analytics_workspace" "workspace" {
  name                = "${var.webapp_name}-logs"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  sku                 = "PerGB2018"
  retention_in_days   = 30
}

resource "azurerm_application_insights" "insights" {
  name                = "${var.webapp_name}-insights"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  application_type    = "web"
  workspace_id        = azurerm_log_analytics_workspace.workspace.id
}

resource "azurerm_monitor_diagnostic_setting" "app-service-diagnostics" {
  name                       = "${var.webapp_name}-logs-and-metrics"
  target_resource_id         = azurerm_linux_web_app.flask_app.id
  log_analytics_workspace_id = azurerm_log_analytics_workspace.workspace.id

  enabled_log  {
    category = "AppServiceHTTPLogs"
  }

  enabled_log  {
    category = "AppServiceConsoleLogs"
  }

  enabled_log  {
    category = "AppServiceAppLogs"
  }

  enabled_log  {
    category = "AppServiceIPSecAuditLogs"
  }

  enabled_log  {
    category = "AppServicePlatformLogs"
  }

  enabled_log  {
    category = "AppServiceAuthenticationLogs"
  }

  metric {
    category = "AllMetrics"
    enabled = false
  }
}