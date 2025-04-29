variable "azure_subscription_id" {
  type        = string
  description = "Azure Subscription ID (stored in .tfvars)"
}

variable "function_name" {
  type        = string
  description = "Azure function name"
}

variable "azure_region" {
  type        = string
  description = "Azure region to use"
  default     = "westeurope"
}