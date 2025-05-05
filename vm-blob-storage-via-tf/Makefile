ifeq ($(OS),Windows_NT)
	SHELL := bash.exe
else
	SHELL := bash
endif

vm_name ?= az-bstore-432
vm_fqdn ?= $(vm_name).westeurope.cloudapp.azure.com
storage_account_name ?= $(shell echo $(vm_name) | sed 's/[^a-zA-Z0-9]//g')storage

ifeq ($(OS),Windows_NT)
	gradle_command = gradlew.bat
else
	gradle_command = ./gradlew
endif

tf_vars = -var-file=.tfvars -var="vm_name=$(vm_name)" -var="storage_account_name=$(storage_account_name)"

ifeq ($(OS),Windows_NT)
	ssh_win_path ?= $(shell cd ~/.ssh && powershell 'Get-Location | Foreach-Object { $$_.Path }')
	proj_win_path ?= $(shell powershell 'Get-Location | Foreach-Object { $$_.Path }')
	ansible_command ?= powershell docker --% run -ti --rm \
		-v '$(ssh_win_path)':'/root/.ssh' \
		-v '$(proj_win_path)':'/proj' \
		-w '/proj' \
		alpine/ansible ansible-playbook
else
	ansible_command = ansible-playbook
endif

app_insights_conn ?= $(shell az monitor app-insights component show --app $(vm_name)-insights --resource-group $(vm_name)-rg --query connectionString --output tsv)
ansible_opts ?= -i 'infra/ansible/.inventory.yaml' \
				-e 'app_insights_conn=$(app_insights_conn)' \
				-e 'key_vault_endpoint=https://$(vm_name)-kv.vault.azure.net/' \
				-e 'storage_account_name=$(storage_account_name)'
ansible_playbook = infra/ansible/deploy-to-host.yaml

base_url ?= http://$(vm_name).westeurope.cloudapp.azure.com:8080

n = 50

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: tf-init tf-plan tf-apply tf-destroy

infra/azure/.tfvars:
	@printf 'azure_subscription_id=$(shell az account show --query "id")\n' > infra/azure/.tfvars

# Usage: make tf-init
tf-init:
	terraform -chdir=infra/azure init -var-file=.tfvars

# Usage: make tf-plan
tf-plan: infra/azure/.tfvars
	terraform -chdir=infra/azure plan $(tf_vars)

# Usage: make tf-apply
tf-apply: infra/azure/.tfvars
	terraform -chdir=infra/azure apply $(tf_vars)

# Usage: make tf-destroy
tf-destroy: infra/azure/.tfvars
	terraform -chdir=infra/azure destroy $(tf_vars)

.PHONY: build 

# Usage: make build
build:
	$(gradle_command) -x test build

.PHONY: deploy

# Usage: make deploy
deploy: build
	VM_FQDN=$(vm_fqdn) envsubst < infra/ansible/inventory.yaml > infra/ansible/.inventory.yaml
	$(ansible_command) $(ansible_opts) $(ansible_playbook)

.PHONY: send-health-request send-test-request send-test-requests

# Usage: make send-health-request
send-health-request:
	curl $(base_url)/actuator/health

# Usage: make send-test-request
send-test-request:
	curl -X POST $(base_url)/todos/todo1 -d 'Some text'
	curl $(base_url)/todos/todo1

# Usage: make send-test-requests [n=50]
send-test-requests:
	curl -X POST $(base_url)/todos/todo1 -d 'Some text'
	for i in {1..$(n)}; do curl $(base_url)/todos/todo1; done