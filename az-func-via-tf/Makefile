SHELL := bash

ifeq ($(OS),Windows_NT)
    activate_venv = source .venv/Scripts/activate
else
    activate_venv = source .venv/bin/activate
endif

function_app ?= azure-func-tf-a0c87e61

n ?= 50
ifdef name
	test_query_str = ?name=$(name)
else
	test_query_str =
endif

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: venv

# Usage: make venv
venv:
	python -m venv .venv
	$(activate_venv) && pip install -r func_code/requirements.txt

.PHONY: tf-init tf-plan tf-apply tf-destroy

# Usage: make tf-init
tf-init:
	terraform -chdir=infra init -var-file=.tfvars

# Usage: make tf-plan
tf-plan:
	terraform -chdir=infra plan -var-file=.tfvars

# Usage: make tf-apply
tf-apply:
	terraform -chdir=infra apply -var-file=.tfvars

# Usage: make tf-destroy
tf-destroy:
	terraform -chdir=infra destroy -var-file=.tfvars

.PHONY: send-test-request send-test-requests

# Usage: make send-test-request [name=xyz]
send-test-request:
	curl https://$(function_app).azurewebsites.net/api/say_hello$(test_query_str)

# Usage: make send-test-requests [n=50] [name=xyz]
send-test-requests:
	for i in {1..$(n)}; do curl https://$(function_app).azurewebsites.net/api/say_hello$(test_query_str); done