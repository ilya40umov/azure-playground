variable "azure_subscription_id" {
  type        = string
  description = "Azure Subscription ID (stored in .tfvars)"
}

variable "webapp_name" {
  type        = string
  description = "App service name"
}

variable "azure_region" {
  type        = string
  description = "Azure region to use"
  default     = "westeurope"
}