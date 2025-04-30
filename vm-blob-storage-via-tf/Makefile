SHELL := bash

ifeq ($(OS),Windows_NT)
	gradle_command = gradlew.bat
	ansible_command = docker run -ti --rm -v /${HOME}/.ssh://root/.ssh -v /$(shell pwd)://proj -e ANSIBLE_CONFIG=infra/ansible/ansible.cfg -w //proj alpine/ansible ansible-playbook
else
	gradle_command = ./gradlew
	ansible_command = ANSIBLE_CONFIG=infra/ansible/ansible.cfg ansible-playbook
endif

vm_name ?= az-bstore-432

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
	terraform -chdir=infra/azure plan -var-file=.tfvars -var="vm_name=$(vm_name)"

# Usage: make tf-apply
tf-apply: infra/azure/.tfvars
	terraform -chdir=infra/azure apply -var-file=.tfvars -var="vm_name=$(vm_name)"

# Usage: make tf-destroy
tf-destroy: infra/azure/.tfvars
	terraform -chdir=infra/azure destroy -var-file=.tfvars -var="vm_name=$(vm_name)"

.PHONY: build 

# Usage: make build
build:
	$(gradle_command) -x test build

.PHONY: deploy

# Usage: make deploy
deploy: #build
	$(ansible_command) -i $(vm_name).westeurope.cloudapp.azure.com, infra/ansible/deploy-to-host.yaml
