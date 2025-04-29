resource "azurerm_service_plan" "plan" {
  name                = "${var.function_name}-plan"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  os_type             = "Linux"
  sku_name            = "Y1"
}

data "archive_file" "function_app_zip" {
  type       = "zip"
  source_dir = "../func_code"
  output_path = "archives/${sha1(join("", [for f in fileset("../func_code", "**") : filesha1("../func_code/${f}")]))}-function.zip"
}

resource "azurerm_linux_function_app" "function" {
  name                       = var.function_name
  location                   = azurerm_resource_group.rg.location
  resource_group_name        = azurerm_resource_group.rg.name

  service_plan_id            = azurerm_service_plan.plan.id
  storage_account_name       = azurerm_storage_account.storage.name
  storage_account_access_key = azurerm_storage_account.storage.primary_access_key

  site_config {
    application_stack {
      python_version = "3.12"
    }
    application_insights_connection_string = azurerm_application_insights.insights.connection_string
    application_insights_key               = azurerm_application_insights.insights.instrumentation_key
  }

  zip_deploy_file = data.archive_file.function_app_zip.output_path

  app_settings = {
    AzureWebJobsFeatureFlags              = "EnableWorkerIndexing"
    SCM_DO_BUILD_DURING_DEPLOYMENT        = true
    ENABLE_ORYX_BUILD                     = true
  }

  identity {
    type = "SystemAssigned"
  }

  lifecycle {
    ignore_changes = [tags]
  }
}
