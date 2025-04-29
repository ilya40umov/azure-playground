resource "azurerm_service_plan" "app_service_plan" {
  name                = "${var.webapp_name}-plan"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"
  sku_name            = "B1"
}

resource "azurerm_linux_web_app" "flask_app" {
  name                = var.webapp_name
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  service_plan_id     = azurerm_service_plan.app_service_plan.id

  site_config {
    application_stack {
      python_version = "3.12"
    }
  }

  app_settings = {
    "WEBSITES_ENABLE_APP_SERVICE_STORAGE" = "false"
    "SCM_DO_BUILD_DURING_DEPLOYMENT"      = "true"
    "APPLICATIONINSIGHTS_CONNECTION_STRING"  = azurerm_application_insights.insights.connection_string
    "APPINSIGHTS_INSTRUMENTATIONKEY"         = azurerm_application_insights.insights.instrumentation_key
    "ApplicationInsightsAgent_EXTENSION_VERSION" = "~3"
    "APPLICATIONINSIGHTS_ROLE_NAME" = var.webapp_name
  }

  https_only = true
}