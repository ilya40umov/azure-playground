variable "azure_subscription_id" {
  type        = string
  description = "Azure Subscription ID (stored in .tfvars)"
}

variable "vm_name" {
  type        = string
  description = "Name of the VM"
}

variable "azure_region" {
  type        = string
  description = "Azure region to use"
  default     = "westeurope"
}

variable "admin_username" {
  type        = string
  description = "Admin username for the VM"
  default     = "azvmuser"
}

variable "ssh_public_key_path" {
  type        = string
  description = "Path to your public SSH key (e.g., ~/.ssh/id_ed25519.pub)"
  default     = "~/.ssh/id_ed25519.pub"
}
