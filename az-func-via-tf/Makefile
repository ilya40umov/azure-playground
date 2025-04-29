SHELL := bash

ifeq ($(OS),Windows_NT)
    activate_venv = source .venv/Scripts/activate
else
    activate_venv = source .venv/bin/activate
endif

function_name ?= azure-func-42-tf

n ?= 50
ifdef name
	test_query_str = ?name=$(name)
else
	test_query_str =
endif
test_request_url = https://$(function_name).azurewebsites.net/api/say_hello$(test_query_str)

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: venv

# Usage: make venv
venv:
	python -m venv .venv
	$(activate_venv) && pip install -r func_code/requirements.txt

infra/.tfvars:
	@printf 'azure_subscription_id=$(shell az account show --query "id")\n' > infra/.tfvars
	@printf 'function_name="$(function_name)"' >> infra/.tfvars

.PHONY: tf-init tf-plan tf-apply tf-destroy

# Usage: make tf-init
tf-init:
	terraform -chdir=infra init

# Usage: make tf-plan
tf-plan: infra/.tfvars
	terraform -chdir=infra plan -var-file=.tfvars

# Usage: make tf-apply
tf-apply: infra/.tfvars
	terraform -chdir=infra apply -var-file=.tfvars

# Usage: make tf-destroy
tf-destroy: infra/.tfvars
	terraform -chdir=infra destroy -var-file=.tfvars

.PHONY: send-test-request send-test-requests

# Usage: make send-test-request [name=xyz]
send-test-request:
	curl $(test_request_url)

# Usage: make send-test-requests [n=50] [name=xyz]
send-test-requests:
	for i in {1..$(n)}; do curl $(test_request_url); done