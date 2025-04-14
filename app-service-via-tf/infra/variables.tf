variable "azure_subscription_id" {
  type        = string
  description = "Azure Subscription ID (stored in .tfvars)"
}

variable "azure_region" {
  type        = string
  description = "Azure region to use"
  default     = "westeurope"
}