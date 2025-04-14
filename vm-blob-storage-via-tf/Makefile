SHELL := bash

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: build rebuild

# Usage: make build
build:
	./gradlew -x test build

rebuild: build

.PHONY: tf-init tf-apply tf-destroy

# Usage: make tf-init
tf-init:
	terraform -chdir=infra/azure init -var-file=.tfvars

# Usage: make tf-plan
tf-plan:
	terraform -chdir=infra/azure plan -var-file=.tfvars

# Usage: make tf-apply
tf-apply:
	terraform -chdir=infra/azure apply -var-file=.tfvars

# Usage: make tf-destroy
tf-destroy:
	terraform -chdir=infra/azure destroy -var-file=.tfvars

.PHONY: deploy

# Usage: make deploy
deploy:
	ansible-playbook -i infra/ansible/inventory.ini infra/ansible/deploy-to-host.yaml